package de.flaflo.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zuständig für den Apple Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandOPApple implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		Player p = (Player) arg0;

		int amount = 1;
		
		if (args.length == 1) {
			try {
				amount = Integer.parseInt(args[0]);
			} catch (Exception ex) {
				p.sendMessage("§7[§aOPApple§7]§r §cDu musst eine Zahl angeben!");
			}
		}
		
		p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, amount));
		
		if (amount > 1)
			p.sendMessage("§7[§aOPApple§7]§r Du hast §b" + amount + " Äpfel§r erhalten.");
		else
			p.sendMessage("§7[§aOPApple§7]§r Du hast §b" + amount + " Apfel§r erhalten.");

		return false;
	}


}
