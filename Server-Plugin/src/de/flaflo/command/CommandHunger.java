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
 * Zuständig für den Hunger Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandHunger extends Command {

	private static ArrayList<UUID> playersBlockedHunger = new ArrayList<UUID>();
	
	public CommandHunger()
	{
		super("hunger");
	}
	
	@Override
	public boolean execute(final CommandSender arg0, final String arg2, final String[] args) {
		if (args.length == 0) {
			final Player p = (Player) arg0;

			if (!playersBlockedHunger.contains(p.getUniqueId())) {
				playersBlockedHunger.add(p.getUniqueId());
				
				Main.getInstance().sendMessageLang(p, "Hunger", Dictionary.HUNGER_OFF);
			} else {
				playersBlockedHunger.remove(p.getUniqueId());
				
				Main.getInstance().sendMessageLang(p, "Hunger", Dictionary.HUNGER_ON);
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
