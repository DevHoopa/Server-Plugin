package de.flaflo.command;

import java.util.Date;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

/**
 * Zuständig für den Damage Befehl
 * 
 * @author Flaflo
 */
public class CommandWhois implements CommandExecutor {

	public CommandWhois() {

	}

	@Override
	public boolean onCommand(final CommandSender cs, final Command command, final String label, final String[] args) {
		final Player player = Bukkit.getServer().getPlayer(args[0]);
		if (cs.isOp()) {
			if (player != null) {
				cs.sendMessage("§7################################################");
				cs.sendMessage("§7[§aWhois§7]§c OP: " + player.isOp());
				cs.sendMessage("§7[§aWhois§7]§c Whitelist: " + player.isWhitelisted());
				cs.sendMessage("§7[§aWhois§7]§c Fyling Allowed/Flying: " + player.getAllowFlight() + " / " + player.isFlying());
				cs.sendMessage("§7[§aWhois§7]§c First Played: " + new Date(player.getFirstPlayed()).toString());

				String potionEffects = "";
				final Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();
				while (iterator.hasNext())
					potionEffects += iterator.next().toString() + (iterator.hasNext() ? ", " : "");

				cs.sendMessage("§7[§aWhois§7]§c PotionEffects: " + potionEffects);

				cs.sendMessage("§7################################################");
			} else {
				@SuppressWarnings("deprecation")
				final
				OfflinePlayer ply = Bukkit.getOfflinePlayer(args[1]);

				cs.sendMessage("§7################################################");
				cs.sendMessage("§7[§aWhois§7]§c OP: " + ply.isOp());
				cs.sendMessage("§7[§aWhois§7]§c Whitelist: " + ply.isWhitelisted());
				cs.sendMessage("§7[§aWhois§7]§c Banned: " + ply.isBanned());
				cs.sendMessage("§7[§aWhois§7]§c Last Played: " + new Date(ply.getLastPlayed()).toString());
				cs.sendMessage("§7[§aWhois§7]§c First Played: " + new Date(ply.getFirstPlayed()).toString());
				cs.sendMessage("§7################################################");
			}

		} else
			Main.getInstance().broadcastMessageLang("Whois", Dictionary.ADMIN_RESTRICTED);

		return true;
	}

}
