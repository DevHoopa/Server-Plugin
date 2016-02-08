package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClear implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		
		((Player) arg0).getInventory().clear();
		
		arg0.sendMessage("§7[§aClear§7]§r Dein Inventar wurde geleert.");

		return true;
	}

}
