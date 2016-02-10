package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.flaflo.util.UMisc;

public class CommandFreebuild implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0)
			if (arg0.isOp())
				arg0.sendMessage("§7[§aFreebuild§7]§c /freebuild clear");
			else
				arg0.sendMessage("§7[§aFreebuild§7]§c Du besitzt nicht genügend Rechte!");
		else if (args.length == 1) {
			if (arg0.isOp()) {
				UMisc.resetFreebuild();
				
				arg0.sendMessage("§7[§aFreebuild§7]$r Freebuild erfolgreich zurückgesetzt.");
			} else
				arg0.sendMessage("§7[§aFreebuild§7]§c Du besitzt nicht genügend Rechte!");
		} else
			arg0.sendMessage("§7[§aFreebuild§7]§c Du besitzt nicht genügend Rechte!");
		
		return true;
	}

}
