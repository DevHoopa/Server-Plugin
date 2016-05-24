package de.flaflo.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import de.flaflo.util.UPlayer;

/**
 * Zuständig für den Warp Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandWarp extends Command{

	public CommandWarp()
	{
		super("warp");
	}
	
	@Override
	public boolean execute(final CommandSender arg0, final String arg2, final String[] args) {
		final Player p = (Player) arg0;

		if (args.length == 0) {
			Main.getInstance().sendMessageLang(p, "Warp", Dictionary.WARP_INFO1);
			Main.getInstance().sendMessageLang(p, "Warp", Dictionary.WARP_INFO2);
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("create") && !p.isOp()) {
				Main.getInstance().sendMessageLang(p, "Warp", Dictionary.ADMIN_RESTRICTED);
				return false;
			} else if (args[0].equalsIgnoreCase("remove")) {
				if (args.length >= 2) {
					Main.getInstance().getSettings().removeWarp(args[1]);
					arg0.sendMessage("§7[§aWarp§7]§r Warp " + args[1] + " entfernt.");
				}
			} else if (args[0].equalsIgnoreCase("list")) {
				final StringBuilder sb = new StringBuilder();
				
				final Object[] warpNodes = Main.getInstance().getSettings().getWarps().toArray();
				
				for (int i = 0; i < warpNodes.length; i++) {
					final Object warpNode = warpNodes[i];
					
					if (warpNode instanceof String) {
						final String warpName = (String) warpNode;
						
						sb.append(warpName);
						
						if (i < (warpNodes.length - 1))
							sb.append(", ");
					}
				}
				p.sendMessage("§7[§aWarp§7]§r Warps: " + sb.toString());
				
				return false;
			} else if (args[0].equalsIgnoreCase("spawn")) {
				
				if (UPlayer.TELEPORTING_PLAYERS.contains(p))
					return false;
				
				UPlayer.spawn(p, true);
				
				return false;
			}
			
			if (UPlayer.TELEPORTING_PLAYERS.contains(p))
				return false;
			
			final String warpName = args[0].toLowerCase();

			final Location warpPoint = Main.getInstance().getSettings().getWarp(warpName);
			
			if (warpPoint != null) {
				Main.getInstance().sendMessageLang(p, "Warp", Dictionary.WARP_SUCCESS, new ArgumentPair("warp", warpName));
				
				UPlayer.teleport(p, Main.getInstance().getSettings().getWarp(warpName), 2L);
			} else
				Main.getInstance().sendMessageLang(p, "Warp", Dictionary.WARP_NOT_EXIST);
		} else if (args.length == 2)
			if (args[0].equalsIgnoreCase("create") && (p.isOp() || p.hasPermission("server.warp.create"))) {
				final String warpName = args[1].toLowerCase();
				
				Main.getInstance().getSettings().setWarp(p.getLocation(), warpName);

				p.sendMessage("§7[§aWarp§7]§r Warp " + warpName + " gesetzt.");
			} else
				Main.getInstance().sendMessageLang(p, "Warp", Dictionary.ADMIN_RESTRICTED);

		return false;
	}

}
