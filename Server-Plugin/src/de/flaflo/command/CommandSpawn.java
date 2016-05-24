package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import de.flaflo.util.UPlayer;

/**
 * Zuständig für den Spawn Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandSpawn extends Command {

	public CommandSpawn()
	{
		super("spawn");
	}
	
	@Override
	public boolean execute(final CommandSender arg0, final String arg2, final String[] args) {
		if (args.length == 0) {
			final Player p = (Player) arg0;

			if (UPlayer.TELEPORTING_PLAYERS.contains(p))
				return false;
			
			UPlayer.spawn(p, true);
		} else if (args.length == 1) {
			final Player p = (Player) arg0;

			if (args[0].equalsIgnoreCase("set") && (p.isOp() || p.hasPermission("server.spawn.set"))) {
				Main.getInstance().getSettings().setSpawn(p.getLocation());

				p.sendMessage("§7[§aSpawn§7]§r Spawn gesetzt.");
			} else
				Main.getInstance().sendMessageLang(p, "Spawn", Dictionary.ADMIN_RESTRICTED);
		}

		return false;
	}

}
