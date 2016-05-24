package de.flaflo.command;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import de.flaflo.util.UMisc;
import de.flaflo.util.UPlayer;

/**
 * Zuständig für den TPA Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandTPA extends Command {

	private static final HashMap<UUID, UUID> requestQueue = new HashMap<UUID, UUID>();
	
	public CommandTPA()
	{
		super("tpa");
	}
	
	@Override
	public boolean execute(final CommandSender arg0, final String arg2, final String[] args) {
		final Player p = (Player) arg0;
		final UUID pId = p.getUniqueId();
		
		if (args.length == 0) {
			Main.getInstance().sendMessageLang(p, "TPA", Dictionary.TPA_INFO1);
			Main.getInstance().sendMessageLang(p, "TPA", Dictionary.TPA_INFO2);
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("accept")) {
				
				if (requestQueue.containsValue(pId)) {
					final UUID tpId = UMisc.getKeyByValue(requestQueue, pId);
					final Player tp = Main.getInstance().getServer().getPlayer(tpId);
					
					if ((tp != null) && tp.isOnline()) {
						requestQueue.remove(pId);
						Main.getInstance().sendMessageLang(p, "TPA", Dictionary.TPA_SUCCESS, new ArgumentPair("player", p.getName()));

						UPlayer.teleport(tp, p, 2L);
					} else
						Main.getInstance().sendMessageLang(p, "TPA", Dictionary.PLAYER_NOT_FOUND);
				} else
					Main.getInstance().sendMessageLang(p, "TPA", Dictionary.TPA_NO_REQUESTS);
				
				return false;
			}
			
			final Player to = Main.getInstance().getServer().getPlayer(args[0]);
			final UUID toId = Main.getInstance().getServer().getPlayer(args[0]).getUniqueId();
			
			if ((to != null) && to.isOnline()) {
				if (to.equals(p)) {
					Main.getInstance().sendMessageLang(p, "TPA", Dictionary.TPA_ERROR);
					return false;
				}

				requestQueue.put(pId, toId);
				
				Main.getInstance().sendMessageLang(p, "TPA", Dictionary.TPA_SENT);
				Main.getInstance().sendMessageLang(to, "TPA", Dictionary.TPA_INFO3, new ArgumentPair("player", p.getName()));
				Main.getInstance().sendMessageLang(to, "TPA", Dictionary.TPA_INFO4);
			} else
				Main.getInstance().sendMessageLang(p, "TPA", Dictionary.PLAYER_NOT_FOUND);
		}
		
		return false;
	}
	
}
