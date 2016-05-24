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
 * Zuständig für den Damage Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandDamage implements CommandExecutor {

	private static final ArrayList<UUID> damageablePlayers = new ArrayList<UUID>();
	
	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		if (args.length == 0) {
			final Player p = (Player) arg0;

			if (!damageablePlayers.contains(p.getUniqueId())) {
				damageablePlayers.add(p.getUniqueId());
				
				Main.getInstance().sendMessageLang(p, "Damage", Dictionary.DAMAGE_ON);
			} else {
				damageablePlayers.remove(p.getUniqueId());
				
				Main.getInstance().sendMessageLang(p, "Damage", Dictionary.DAMAGE_OFF);
			}
		}
		
		return false;
	}

	/**
	 * Gibt die Spieler zurück, die Schaden erlauben
	 * @return ArrayList<UUID>
	 */
	public static ArrayList<UUID> getDamageablePlayers() {
		return damageablePlayers;
	}
	
}
