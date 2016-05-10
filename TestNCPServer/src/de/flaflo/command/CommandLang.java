package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.LanguageManager;

public class CommandLang implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		if (args.length == 1) {
			final Player p = (Player) arg0;
			
			switch (args[0].toLowerCase()) {
				case "de":
					LanguageManager.getInstance().setCurrentLang(p, LanguageManager.getInstance().getLanguages()[1]);
					break;
				case "en":
					LanguageManager.getInstance().setCurrentLang(p, LanguageManager.getInstance().getLanguages()[0]);
					break;
				default:
					LanguageManager.getInstance().setCurrentLang(p, LanguageManager.getInstance().getLanguages()[0]);
					break;
			}

		} else
			arg0.sendMessage("§7[§aLanguage§7]§6 Current Language is §l" + LanguageManager.getInstance().getCurrentLang((Player) arg0).getName().toUpperCase());
		
		return false;
	}

}
