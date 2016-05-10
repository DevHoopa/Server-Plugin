package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import de.flaflo.util.UMisc;

public class CommandFreebuild implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		if (args.length == 0)
			if (arg0.isOp() || arg0.hasPermission("freebuild"))
				arg0.sendMessage("§7[§aFreebuild§7]§c /freebuild clear");
			else
				arg0.sendMessage("§7[§aFreebuild§7]§c Du besitzt nicht genügend Rechte!");
		else if (args.length == 1) {
			if (arg0.isOp() || arg0.hasPermission("freebuild.clear")) {
				UMisc.resetFreebuild();
				
				arg0.sendMessage("§7[§aFreebuild§7]§c Freebuild erfolgreich zurückgesetzt.");
			} else
				arg0.sendMessage("§7[§aFreebuild§7]§c Du besitzt nicht genügend Rechte!");
		} else
			Main.getInstance().sendMessageLang((Player) arg0, "Freebuild", Dictionary.ADMIN_RESTRICTED);
		
		return true;
	}

}
