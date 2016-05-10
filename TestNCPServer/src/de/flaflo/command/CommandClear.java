package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import de.flaflo.util.UMisc;

public class CommandClear implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		
		if (args.length == 0) {
			((Player) arg0).getInventory().clear();
			
			Main.getInstance().sendMessageLang((Player) arg0, "Clear", Dictionary.CLEAR_SUCCESS);
		} else if (args.length == 1) {
			if (arg0.isOp()) {
				if (args[0].equalsIgnoreCase("lag"))
					UMisc.clearLag();
				else {
					final Player player = Main.getInstance().getServer().getPlayer(args[0]);
					
					if (player != null) {
						player.getInventory().clear();
						
						Main.getInstance().sendMessageLang((Player) arg0, "Clear", Dictionary.CLEAR_ADMIN_SUCCESS, new ArgumentPair("player", player.getName()));
					} else
						Main.getInstance().sendMessageLang((Player) arg0, "Clear", Dictionary.PLAYER_NOT_FOUND, new ArgumentPair("player", args[0]));
				}
			} else
				Main.getInstance().sendMessageLang((Player) arg0, "Clear", Dictionary.ADMIN_RESTRICTED);
		} else
			arg0.sendMessage("§7[§aClear§7]§c /clear");
		
		return true;
	}

}
