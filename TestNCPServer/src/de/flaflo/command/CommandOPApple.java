package de.flaflo.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

/**
 * Zuständig für den Apple Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandOPApple implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		final Player p = (Player) arg0;

		int amount = 1;
		
		if (args.length == 1)
			try {
				amount = Integer.parseInt(args[0]);
			} catch (final Exception ex) { 	}
		
		p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, amount));
		
		if (amount > 1)
			Main.getInstance().sendMessageLang(p, "Apple", Dictionary.APPLE_MORE, new ArgumentPair("amount", "§b" + amount));
		else
			Main.getInstance().sendMessageLang(p, "Apple", Dictionary.APPLE_ONE, new ArgumentPair("amount", "§b" + amount));

		return false;
	}


}
