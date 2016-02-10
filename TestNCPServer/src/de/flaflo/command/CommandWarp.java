package de.flaflo.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;
import de.flaflo.util.UPlayer;

/**
 * Zuständig für den Warp Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandWarp implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		Player p = (Player) arg0;

		if (args.length == 0) {
			p.sendMessage("§7[§aWarp§7]§r Benutze /warp <name> um dich zu teleportieren.");
			p.sendMessage("§7[§aWarp§7]§r Oder /warp list für eine Liste der Warps.");
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("create") && !p.isOp()) {
				p.sendMessage("§7[§aWarp§7]§r §cDu hast nicht genügend Berechtigungen.");
				
				return false;
			} else if (args[0].equalsIgnoreCase("list")) {
				StringBuilder sb = new StringBuilder();
				
				Object[] warpNodes = Main.getInstance().getSettings().getWarps().toArray();
				
				for (int i = 0; i < warpNodes.length; i++) {
					Object warpNode = warpNodes[i];
					
					if (warpNode instanceof String) {
						String warpName = (String) warpNode;
						
						sb.append(warpName);
						
						if (i < warpNodes.length - 1)
							sb.append(", ");
					}
				}
				
				p.sendMessage("§7[§aWarp§7]§r Warps: " + sb.toString());
				
				return false;
			} else if (args[0].equalsIgnoreCase("spawn")) {
				
				if (UPlayer.TELEPORTING_PLAYERS.contains(p))
					return false;
				
				p.sendMessage("§7[§aWarp§7]§r Teleportiere zu spawn...");

				UPlayer.spawn(p, true);
				
				return false;
			}
			
			if (UPlayer.TELEPORTING_PLAYERS.contains(p))
				return false;
			
			final String warpName = args[0].toLowerCase();

			final Location warpPoint = Main.getInstance().getSettings().getWarp(warpName);
			
			if (warpPoint != null) {
				p.sendMessage("§7[§aWarp§7]§r Teleportiere zu " + warpName +  "...");

				UPlayer.teleport(p, Main.getInstance().getSettings().getWarp(warpName), 2L);
				
			} else
				p.sendMessage("§7[§aWarp§7]§r §cDer Warp " + warpName +  " existiert nicht.");
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("create") && (p.isOp() || p.hasPermission("server.warp.create"))) {
				String warpName = args[1].toLowerCase();
				
				Main.getInstance().getSettings().setWarp(p.getLocation(), warpName);

				p.sendMessage("§7[§aWarp§7]§r Warp " + warpName +" gesetzt.");
			} else
				p.sendMessage("§7[§aWarp§7]§r §cDu hast nicht genügend Berechtigungen.");
		}

		return false;
	}

}
