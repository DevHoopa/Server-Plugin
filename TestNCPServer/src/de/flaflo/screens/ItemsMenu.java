package de.flaflo.screens;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;
import de.flaflo.screen.gui.buttons.InteractButton;
import de.flaflo.screen.gui.event.ButtonClickEvent;
import de.flaflo.screen.gui.screens.InventoryScreen;

public class ItemsMenu extends InventoryScreen {

	public ItemsMenu(Player viewer) {
		super(Main.getInstance(), "Item Menu", viewer, 9, 1);
	}

	@Override
	public void init() {
		this.registerButton(new InteractButton(0, "Test", Material.DIAMOND));
	}

	@Override
	public void onButtonPressed(ButtonClickEvent e) {
		switch (e.getButton().getId()) {
			case 0:
				e.getPlayer().sendMessage("Das ist eine Testnachricht");
				break;
			default:
				break;
		}
	}

	@Override
	public void onScreenClosed() {
		
	}

	@Override
	public void onScreenDispose() {
		
	}

	@Override
	public void onScreenOpened() {
		
	}

	@Override
	public void onScreenUpdate() {
		
	}

}
