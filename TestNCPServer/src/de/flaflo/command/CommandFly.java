package de.flaflo.command;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

/**
 * Zuständig für den Fly Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandFly implements CommandExecutor {
	
	/** Spieler denen es erlaubt ist zu fliegen */
	private static final ArrayList<UUID> playersAllowedFlying = new ArrayList<UUID>();
	
	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {

		if (args.length == 0) {
			final Player p = (Player) arg0;

			if (!playersAllowedFlying.contains(p.getUniqueId())) {
				p.setAllowFlight(true);
				
				playersAllowedFlying.add(p.getUniqueId());
				
				Main.getInstance().sendMessageLang(p, "Fly", Dictionary.FLY_ON);
			} else {
				p.setFlying(false);
				p.setAllowFlight(false);
				
				playersAllowedFlying.remove(p.getUniqueId());
				
				Main.getInstance().sendMessageLang(p, "Fly", Dictionary.FLY_OFF);
			}
		}
		
		return false;
	}

	public static ArrayList<UUID> getPlayersallowedflying() {
		return playersAllowedFlying;
	}
	
	
}
