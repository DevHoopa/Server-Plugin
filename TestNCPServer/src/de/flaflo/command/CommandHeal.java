package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

/**
 * Zuständig für den Heal Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandHeal implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		if (args.length == 0) {
			final Player p = (Player) arg0;

			p.setHealth(p.getMaxHealth());
			p.setFoodLevel(20);
			p.setSaturation(20);
			
			Main.getInstance().sendMessageLang(p, "Heal", Dictionary.HEAL_SUCCESS);
		}

		return false;
	}

}
