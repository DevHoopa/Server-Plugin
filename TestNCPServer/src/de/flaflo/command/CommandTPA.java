package de.flaflo.command;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;
import de.flaflo.util.UMisc;
import de.flaflo.util.UPlayer;

/**
 * Zuständig für den TPA Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandTPA implements CommandExecutor {

	private static HashMap<UUID, UUID> requestQueue = new HashMap<UUID, UUID>();
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		Player p = (Player) arg0;
		UUID pId = p.getUniqueId();
		
		if (args.length == 0) {
			p.sendMessage("§7[§aTPA§7]§r Um eine Anfrage zu senden mache /tpa <name>.");
			p.sendMessage("§7[§aTPA§7]§r Um eine Anfrage anzunehmen mache /tpa accept.");
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("accept")) {
				
				if (requestQueue.containsValue(pId)) {
					UUID tpId = UMisc.getKeyByValue(requestQueue, pId);
					Player tp = Main.getInstance().getServer().getPlayer(tpId);
					
					if (tp != null && tp.isOnline()) {
						requestQueue.remove(pId);
						
						tp.sendMessage("§7[§aTPA§7]§r Teleportiere zu " + p.getName() + "...");

						UPlayer.teleport(tp, p, 2L);
					} else
						p.sendMessage("§7[§aTPA§7]§r §cDieser Spieler ist nicht online.");
				} else
					p.sendMessage("§7[§aTPA§7]§r Du hast keine Anfrage, die du akzeptieren könntest.");
				
				return false;
			}
			
			Player to = Main.getInstance().getServer().getPlayer(args[0]);
			UUID toId = Main.getInstance().getServer().getPlayer(args[0]).getUniqueId();
			
			if (to != null && to.isOnline()) {
				if (to.equals(p)) {
					p.sendMessage("§7[§aTPA§7]§r §cDu kannst dich nicht zu dir selbst teleportieren!");
					
					return false;
				}

				requestQueue.put(pId, toId);
				
				p.sendMessage("§7[§aTPA§7]§r §aAnfrage gesendet.");

				to.sendMessage("§7[§aTPA§7]§r §a" + p.getName() + " möchte sich zu dir teleportieren.");
				to.sendMessage("§7[§aTPA§7]§r Mache /tpa accept um die Anfrage zu akzeptieren.");
			} else
				p.sendMessage("§7[§aTPA§7]§r §cDieser Spieler ist nicht online.");
		}
		
		return false;
	}
	
}
