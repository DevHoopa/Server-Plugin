package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R2.EntityPlayer;

/**
 * Zuständig für den Ping Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandPing implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Player p = (Player) arg0;

			int ping = 0;

			if (p != null) {
				CraftPlayer cp = (CraftPlayer) p;
				EntityPlayer ep = cp.getHandle();
				
				ping = ep.ping;
			}
			
			p.sendMessage("§7[§aPing§7]§r Dein Ping ist: " + ping);
		}
		
		return false;
	}

}
