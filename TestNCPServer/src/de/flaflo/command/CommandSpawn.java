package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;
import de.flaflo.util.UPlayer;

/**
 * Zuständig für den Spawn Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandSpawn implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Player p = (Player) arg0;

			p.sendMessage("§7[§aSpawn§7]§r Teleportiere zum Spawn...");
		
			UPlayer.spawn(p, true);
		} else if (args.length == 1) {
			Player p = (Player) arg0;

			if (args[0].equalsIgnoreCase("change") && (p.isOp() || p.hasPermission("server.spawn.set"))) {
				Main.getInstance().getSettings().setSpawn(p.getLocation());

				p.sendMessage("§7[§aSpawn§7]§r Spawn gesetzt.");
			} else
				p.sendMessage("§7[§aConsole§7]§r §cDu besitzt nicht genügend Rechte!");
		}

		return false;
	}

}
