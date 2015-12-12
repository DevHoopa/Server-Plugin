package de.flaflo.command;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zust�ndig f�r den Damage Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandDamage implements CommandExecutor {

	private static ArrayList<UUID> damageablePlayers = new ArrayList<UUID>();
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Player p = (Player) arg0;

			if (!damageablePlayers.contains(p.getUniqueId())) {
				damageablePlayers.add(p.getUniqueId());
				
				p.sendMessage("�7[�aSchaden�7]�r Du erlaubst nun Schaden.");
			} else {
				damageablePlayers.remove(p.getUniqueId());
				
				p.sendMessage("�7[�aSchaden�7]�r Du erlaubst Schaden nicht mehr.");
			}
		}
		
		return false;
	}

	/**
	 * Gibt die Spieler zur�ck, die Schaden erlauben
	 * @return ArrayList<UUID>
	 */
	public static ArrayList<UUID> getDamageablePlayers() {
		return damageablePlayers;
	}
	
}
