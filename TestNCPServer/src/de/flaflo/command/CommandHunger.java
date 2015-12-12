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

	private static ArrayList<UUID> playersBlockedHunger = new ArrayList<UUID>();
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Player p = (Player) arg0;

			if (!playersBlockedHunger.contains(p.getUniqueId())) {
				playersBlockedHunger.add(p.getUniqueId());
				
				p.sendMessage("§7[§aHunger§7]§r Du erlaubst Hunger nicht mehr.");
			} else {
				playersBlockedHunger.remove(p.getUniqueId());
				
				p.sendMessage("§7[§aHunger§7]§r Du erlaubst nun Hunger.");
			}
		}
		
		return false;
	}

	/**
	 * Gibt Spieler zurück, die Hunger blockieren
	 * @return ArrayList<UUID>
	 */
	public static ArrayList<UUID> getPlayersBlockedHunger() {
		return playersBlockedHunger;
	}
}
