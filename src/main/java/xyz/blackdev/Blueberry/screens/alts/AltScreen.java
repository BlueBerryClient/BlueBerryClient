/*
 * Aoba Hacked Client
 * Copyright (C) 2019-2024 coltonk9043
 *
 * Licensed under the GNU General Public License, Version 3 or later.
 * See <http://www.gnu.org/licenses/>.
 */

package xyz.blackdev.Blueberry.screens.alts;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import xyz.blackdev.Blueberry.ModEntryPoint;
import xyz.blackdev.Blueberry.features.altmanager.Alt;

public class AltScreen extends Screen {


	private final Screen parentScreen;
	private ButtonWidget editButton;
	private ButtonWidget deleteButton;
	private AltSelectionList altListSelector;

	public AltScreen(Screen parentScreen) {
		super(Text.of("Alt Manager"));
		this.parentScreen = parentScreen;
	}

	public void init() {
		super.init();

		this.altListSelector = new AltSelectionList(this, this.client, this.width, this.height, 32, 36);
		this.altListSelector.updateAlts();
		this.altListSelector.setDimensionsAndPosition(this.width, this.height - 64 - 32, 0, 32);
		this.addDrawableChild(this.altListSelector);

		this.deleteButton = ButtonWidget.builder(Text.of("Delete Alt"), b -> this.deleteSelected())
				.dimensions(this.width / 2 - 154, this.height - 28, 100, 20).build();
		this.deleteButton.active = false;
		this.addDrawableChild(this.deleteButton);

		this.addDrawableChild(
				ButtonWidget.builder(Text.of("Direct Login"), b -> client.setScreen(new DirectLoginAltScreen(this)))
						.dimensions(this.width / 2 - 50, this.height - 52, 100, 20).build());

		this.addDrawableChild(ButtonWidget.builder(Text.of("Add Alt"), b -> client.setScreen(new AddAltScreen(this)))
				.dimensions(this.width / 2 + 4 + 50, this.height - 52, 100, 20).build());

		this.addDrawableChild(ButtonWidget.builder(Text.of("Cancel"), b -> client.setScreen(this.parentScreen))
				.dimensions(this.width / 2 + 54, this.height - 28, 100, 20).build());

		this.editButton = ButtonWidget.builder(Text.of("Edit Alt"), b -> this.editSelected())
				.dimensions(this.width / 2 - 50, this.height - 28, 100, 20).build();
		this.editButton.active = false;
		this.addDrawableChild(this.editButton);
	}

	@Override
	public void tick() {
		AltSelectionList.Entry altselectionlist$entry = this.altListSelector.getSelectedOrNull();
		if (altselectionlist$entry == null)
			return;
	}

	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(drawContext, mouseX, mouseY, partialTicks);
		this.altListSelector.render(drawContext, mouseX, mouseY, partialTicks);

		super.render(drawContext, mouseX, mouseY, partialTicks);
		drawContext.drawCenteredTextWithShadow(textRenderer,
				"Currently Logged Into: " + MinecraftClient.getInstance().getSession().getUsername(), this.width / 2,
				20, 16777215);
	}

	public List<Alt> getAltList() {
		return ModEntryPoint.altmanager.getAlts();
	}

	public void refreshAltList() {
		this.client.setScreen(new AltScreen(this.parentScreen));
	}

	public void setSelected(AltSelectionList.Entry selected) {
		this.altListSelector.setSelected(selected);
		this.setEdittable();
	}

	protected void setEdittable() {
		this.editButton.active = true;
		this.deleteButton.active = true;
	}

	public void loginToSelected() {
		AltSelectionList.Entry altselectionlist$entry = this.altListSelector.getSelectedOrNull();
		if (altselectionlist$entry == null) {
			return;
		}

		Alt alt = ((AltSelectionList.NormalEntry) altselectionlist$entry).getAltData();
		if (alt.isCracked()) {
			ModEntryPoint.altmanager.loginCracked(alt.getEmail());
		} else {
			ModEntryPoint.altmanager.login(alt);
		}
	}

	public void editSelected() {
		Alt alt = ((AltSelectionList.NormalEntry) this.altListSelector.getSelectedOrNull()).getAltData();
		if (alt == null) {
			return;
		}
		client.setScreen(new EditAltScreen(this, alt));
	}

	public void deleteSelected() {
		Alt alt = ((AltSelectionList.NormalEntry) this.altListSelector.getSelectedOrNull()).getAltData();
		if (alt == null) {
			return;
		}
		ModEntryPoint.altmanager.removeAlt(alt);
		this.refreshAltList();
	}

	@Override
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
		super.renderBackground(context, mouseX, mouseY, delta);
	}
}
