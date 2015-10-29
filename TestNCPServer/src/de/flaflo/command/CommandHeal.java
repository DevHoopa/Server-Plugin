package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Zuständig für den Heal Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandHeal implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Player p = (Player) arg0;

			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
			p.setSaturation(20);

			p.sendMessage("§7[§aHeal§7]§r Du wurdest geheilt.");
		}

		return false;
	}

}
