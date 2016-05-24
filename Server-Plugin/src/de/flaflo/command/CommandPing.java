package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;

/**
 * Zuständig für den Ping Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandPing implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		if (args.length == 0) {
			final Player p = (Player) arg0;

			int ping = 0;

			if (p != null) {
				final CraftPlayer cp = (CraftPlayer) p;
				final EntityPlayer ep = cp.getHandle();
				
				ping = ep.ping;
			}
			
			Main.getInstance().sendMessageLang(p, "Ping", Dictionary.PING_INFO, new ArgumentPair("ping", String.valueOf(ping)));
		}
		
		return false;
	}

}
