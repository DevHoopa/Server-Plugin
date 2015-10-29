package de.flaflo.command;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zuständig für den Fly Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandFly implements CommandExecutor {
	
	private static ArrayList<UUID> flying = new ArrayList<UUID>();
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {

		if (args.length == 0) {
			Player p = (Player) arg0;

			if (!flying.contains(p.getUniqueId())) {
				p.setAllowFlight(true);
				
				flying.add(p.getUniqueId());
				
				p.sendMessage("§7[§aFly§7]§r Du fliegst jetzt.");
			} else {
				p.setFlying(false);
				p.setAllowFlight(false);
				
				flying.remove(p.getUniqueId());
				
				p.sendMessage("§7[§aFly§7]§r Du fliegst jetzt nicht mehr.");
			}
		}
		
		return false;
	}
	
}
