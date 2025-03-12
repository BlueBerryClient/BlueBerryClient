/*
 * Aoba Hacked Client
 * Copyright (C) 2019-2024 coltonk9043
 *
 * Licensed under the GNU General Public License, Version 3 or later.
 * See <http://www.gnu.org/licenses/>.
 */

package xyz.blackdev.Blueberry.utils;

import net.minecraft.client.MinecraftClient;
import org.apache.commons.codec.digest.DigestUtils;

public class SystemUtils
{

	static MinecraftClient MC = MinecraftClient.getInstance();
	public static String getSystemSecureVariable() {
		return DigestUtils.sha256Hex(DigestUtils.sha256Hex(System.getenv("os") + System.getProperty("os.name")
				+ System.getProperty("os.arch") + System.getProperty("user.name") + System.getenv("SystemRoot")
				+ System.getenv("HOMEDRIVE") + System.getenv("PROCESSOR_LEVEL") + System.getenv("PROCESSOR_REVISION")
				+ System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE")
				+ System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")))
				+ MC.getSession().getUsername();
	}
}
