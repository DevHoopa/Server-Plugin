package de.flaflo.command;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zuständig für den Hunger Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandHunger implements CommandExecutor {

	public static ArrayList<UUID> hunger = new ArrayList<UUID>();
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Player p = (Player) arg0;

			if (!hunger.contains(p.getUniqueId())) {
				hunger.add(p.getUniqueId());
				
				p.sendMessage("§7[§aHunger§7]§r Du erlaubst Hunger nicht mehr.");
			} else {
				hunger.remove(p.getUniqueId());
				
				p.sendMessage("§7[§aHunger§7]§r Du erlaubst nun Hunger.");
			}
		}
		
		return false;
	}

}
