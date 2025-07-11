/*
 * BlueBerry Client
 * Copyright (C) 2019-2024 BlackDev
 *
 * Licensed under the GNU General Public License, Version 3 or later.
 * See <http://www.gnu.org/licenses/>.
 */

package xyz.blackdev.Blueberry.features.altmanager.login;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.minecraft.UserApiService;
import com.mojang.logging.LogUtils;
import com.mojang.util.UndashedUuid;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.session.ProfileKeys;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.report.AbuseReportContext;
import net.minecraft.client.session.report.ReporterEnvironment;
import net.minecraft.util.Util;
import xyz.blackdev.Blueberry.Blueberry;
import xyz.blackdev.Blueberry.mixin.utils.IMinecraftClient;
import xyz.blackdev.Blueberry.utils.connection.HttpUtils;
import xyz.blackdev.Blueberry.features.altmanager.Alt;

public class MicrosoftAuth {
	private static final String CLIENT_ID = "f98dbf4a-3b56-4af2-91d3-306f045aa3e2";
	private static final URI TOKEN_URL = HttpUtils.createURI("https://login.live.com/oauth20_token.srf");
	private static final URI XBL_URL = HttpUtils.createURI("https://user.auth.xboxlive.com/user/authenticate");
	private static final URI XBLAUTH_URL = HttpUtils.createURI("https://xsts.auth.xboxlive.com/xsts/authorize");
	private static final URI XBLOGIN_URL = HttpUtils
			.createURI("https://api.minecraftservices.com/authentication/login_with_xbox");
	private static final URI ENTITLEMENT_URL = HttpUtils
			.createURI("https://api.minecraftservices.com/entitlements/mcstore");
	private static final URI PROFILE_URL = HttpUtils.createURI("https://api.minecraftservices.com/minecraft/profile");

	private static HttpServer replyServer;

	/**
	 * Logs in to an alt.
	 * 
	 * @param alt Alt to login to.
	 */
	public static void login(Alt alt) {
		try {
			LogUtils.getLogger().info("Logged in to user account");
			AuthToken token = renewAuthToken(alt.getAuthToken());
			XSTSToken xblToken = getXblToken(token);
			XSTSToken xstsToken = getXSTSToken(xblToken);
			MCAuthToken mcAuthToken = getMCAuthToken(xblToken, xstsToken);
			EntitlementToken entitlement = getEntitlementToken(mcAuthToken);

			if (entitlement.hasGame()) {
				ProfileToken profileToken = getProfileToken(mcAuthToken);

				String uuid = profileToken.id;
				String username = profileToken.name;

				alt.setAuthToken(token);

				MinecraftClient MC = MinecraftClient.getInstance();
				IMinecraftClient IMC = (IMinecraftClient) MC;
				Session session = new Session(username, UndashedUuid.fromStringLenient(uuid), mcAuthToken.accessToken,
						Optional.empty(), Optional.empty(), Session.AccountType.MSA);
				IMC.setSession(session);

				UserApiService apiService;
				apiService = IMC.getAuthenticationService().createUserApiService(session.getAccessToken());
				IMC.setUserApiService(apiService);
				IMC.setSocialInteractionsManager(new SocialInteractionsManager(MC, apiService));
				IMC.setProfileKeys(ProfileKeys.create(apiService, session, MC.runDirectory.toPath()));
				IMC.setAbuseReportContext(
						AbuseReportContext.create(ReporterEnvironment.ofIntegratedServer(), apiService));
				IMC.setGameProfileFuture(CompletableFuture.supplyAsync(
						() -> MC.getSessionService().fetchProfile(MC.getSession().getUuidOrNull(), true),
						Util.getIoWorkerExecutor()));

			} else
				throw new Exception("User does not have game.");
		} catch (Exception e) {
			LogUtils.getLogger().error("Could not log in to Microsoft account.");
			LogUtils.getLogger().error(e.getMessage());
		}
	}

	public static AuthToken renewAuthToken(AuthToken authToken) {
		String payload = "client_id=" + CLIENT_ID + "&refresh_token=" + authToken.refreshToken
				+ "&grant_type=refresh_token&redirect_uri=http://127.0.0.1:42069";
		Optional<String> response = HttpUtils.builder(TOKEN_URL).acceptJson().form().post(payload);
		if (response.isPresent()) {
			JsonObject json = new Gson().fromJson(response.get(), JsonObject.class);
			return AuthToken.fromJson(json);
		} else
			throw new IllegalArgumentException("Auth token could not be fetched.");
	}

	/**
	 * Fetches the Xbl token from Microsoft.
	 * 
	 * @param authToken The AuthToken containing the access code and refresh code.
	 * @return Xbl token as a XSTSToken object.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static XSTSToken getXblToken(AuthToken authToken) {
		String payload = "{\"Properties\":{\"AuthMethod\":\"RPS\",\"SiteName\":\"user.auth.xboxlive.com\",\"RpsTicket\":\"d="
				+ authToken.accessToken + "\"},\"RelyingParty\":\"http://auth.xboxlive.com\",\"TokenType\":\"JWT\"}";

		Optional<String> response = HttpUtils.builder(XBL_URL).acceptJson().json().post(payload);

		if (response.isPresent()) {
			JsonObject json = new Gson().fromJson(response.get(), JsonObject.class);
			return XSTSToken.fromJson(json);
		} else
			throw new IllegalArgumentException("Xbl token could not be fetched.");
	}

	/**
	 * Fetches the XSTS token from Microsoft.
	 * 
	 * @param xblToken The XSTS token previously fetched.
	 * @return XSTS token fetched
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static XSTSToken getXSTSToken(XSTSToken xblToken) {
		String payload = "{\"Properties\":{\"SandboxId\":\"RETAIL\",\"UserTokens\":[\"" + xblToken.token
				+ "\"]},\"RelyingParty\":\"rp://api.minecraftservices.com/\",\"TokenType\":\"JWT\"}";

		Optional<String> response = HttpUtils.builder(XBLAUTH_URL).acceptJson().json().post(payload);
		if (response.isPresent()) {
			JsonObject json = new Gson().fromJson(response.get(), JsonObject.class);
			return XSTSToken.fromJson(json);
		} else
			throw new IllegalArgumentException("XSTS token could not be fetched.");
	}

	public static MCAuthToken getMCAuthToken(XSTSToken xblToken, XSTSToken xstsToken) {
		String payload = "{\"identityToken\":\"XBL3.0 x=" + xblToken.displayClaims.xui[0].uhs + ";" + xstsToken.token
				+ "\", \"ensureLegacyEnabled\":true}";

		Optional<String> response = HttpUtils.builder(XBLOGIN_URL).acceptJson().json().post(payload);
		if (response.isPresent()) {
			JsonObject json = new Gson().fromJson(response.get(), JsonObject.class);
			return MCAuthToken.fromJson(json);
		} else
			throw new IllegalArgumentException("MC Auth token could not be fetched.");
	}

	public static EntitlementToken getEntitlementToken(MCAuthToken mcAuthToken) {
		Optional<String> response = HttpUtils.builder(ENTITLEMENT_URL).acceptJson().json()
				.bearer(mcAuthToken.accessToken).get();
		if (response.isPresent()) {
			JsonObject json = new Gson().fromJson(response.get(), JsonObject.class);
			return EntitlementToken.fromJson(json);
		} else
			throw new IllegalArgumentException("Entitlement token could not be fetched.");
	}

	public static ProfileToken getProfileToken(MCAuthToken mcAuthToken) throws IOException, InterruptedException {
		Optional<String> response = HttpUtils.builder(PROFILE_URL).acceptJson().json().bearer(mcAuthToken.accessToken)
				.get();
		if (response.isPresent()) {
			JsonObject json = new Gson().fromJson(response.get(), JsonObject.class);
			return ProfileToken.fromJson(json);
		} else
			throw new IllegalArgumentException("Entitlement token could not be fetched.");
	}

	public static void requestAuthToken(Consumer<AuthToken> onDataReceived) {
		boolean success = startServer(onDataReceived);
		if (success) {
			Util.getOperatingSystem().open("https://login.live.com/oauth20_authorize.srf?client_id=" + CLIENT_ID
					+ "&response_type=code&redirect_uri=http://127.0.0.1:42069&scope=XboxLive.signin%20offline_access&prompt=select_account");
		}
	}

	public static boolean startServer(Consumer<AuthToken> onDataReceived) {
		try {
			replyServer = HttpServer.create(new InetSocketAddress("127.0.0.1", 42069), 0);
			RefreshTokenHandler handler = new RefreshTokenHandler();
			handler.setConsumer(onDataReceived);
			replyServer.createContext("/", handler);
			replyServer.setExecutor(null);
			replyServer.start();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void stopServer() {
		if (replyServer == null)
			return;

		replyServer.stop(0);
		replyServer = null;
	}

	static class RefreshTokenHandler implements HttpHandler {
		private Consumer<AuthToken> onDataReceived;

		@Override
		public void handle(HttpExchange t) throws IOException {
			URI requestURI = t.getRequestURI();
			String query = requestURI.getQuery();
			String[] parameterPairs = query.split("&");

			boolean found = false;
			for (String params : parameterPairs) {
				String[] paramSplit = params.split("=");
				if (paramSplit.length == 2) {
					if (paramSplit[0].equalsIgnoreCase("code")) {
						found = true;
						try {
							AuthToken token = getAccessToken(paramSplit[1]);
							onDataReceived.accept(token);
						} catch (Exception e) {
							LogUtils.getLogger()
									.info("[BlueBerry] Error occured while fetching Access Tokens from Microsoft");
							onDataReceived.accept(null);
						}

						break;
					}
				}
			}

			if (!found) {
				LogUtils.getLogger().info("[BlueBerry] Unable to get Access Tokens from Microsoft");
				onDataReceived.accept(null);
			}

			if (replyServer == null)
				return;

			replyServer.stop(0);
			replyServer = null;
		}

		private AuthToken getAccessToken(String code) throws IOException, InterruptedException {
			String payload = "client_id=" + CLIENT_ID + "&code=" + code
					+ "&grant_type=authorization_code&redirect_uri=http://127.0.0.1:42069";
			Optional<String> response = HttpUtils.builder(TOKEN_URL).acceptJson().form().post(payload);
			if (response.isPresent()) {
				JsonObject json = new Gson().fromJson(response.get(), JsonObject.class);
				return AuthToken.fromJson(json);
			} else
				throw new IllegalArgumentException("Device token could not be fetched.");
		}

		public void setConsumer(Consumer<AuthToken> consumer) {
			onDataReceived = consumer;
		}
	}
}
