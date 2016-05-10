package de.flaflo.command;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import de.flaflo.util.UMisc;
import de.flaflo.util.UPlayer;

public class CommandMute implements CommandExecutor {
	
	public static final Date DATE_INFINITY = new Date(Long.MAX_VALUE);
	
	public static final SimpleDateFormat MUTE_PARSE_FORMAT_1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	public static final SimpleDateFormat MUTE_PARSE_FORMAT_2 = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat MUTE_PARSE_FORMAT_3 = new SimpleDateFormat("mm:ss");
	public static final SimpleDateFormat MUTE_PARSE_FORMAT_4 = new SimpleDateFormat("HH");
	public static final SimpleDateFormat MUTE_PARSE_FORMAT_5 = new SimpleDateFormat("mm");
	public static final SimpleDateFormat MUTE_PARSE_FORMAT_6 = new SimpleDateFormat("ss");
	
	public static final HashMap<UUID, Date> MUTED_PLAYERS = new HashMap<UUID, Date>();
	
	public CommandMute() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (final Date date : MUTED_PLAYERS.values()) {
					if (date.equals(DATE_INFINITY))
						continue;
					
					if (Calendar.getInstance().getTime().equals(date) || Calendar.getInstance().getTime().after(date)) {
						
						final UUID playersUniqueId = UMisc.getKeyByValue(MUTED_PLAYERS, date);
						final Player player = UPlayer.getPlayerByUUID(playersUniqueId);
						
						MUTED_PLAYERS.remove(playersUniqueId);
						
						if (player != null)
							player.sendMessage("§7[§aMute§7]§a Deine Mutezeit ist abgelaufen.");
					}
				}
			}
		}.runTaskTimer(Main.getInstance(), 20L, 20L);
	}
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command arg1, final String arg2, final String[] args) {
		
		if (sender.isOp() || sender.hasPermission("player.mute")) {
			if (args.length == 0)
				sender.sendMessage("§7[§aMute§7]§c /mute <player> <länge>");
			else if (args.length == 2) {
				final Player playerToMute = Main.getInstance().getServer().getPlayer(args[0]);
				
				if (playerToMute != null)
					try {
						Date date = new Date(MUTE_PARSE_FORMAT_2.parse(args[1]).getTime() + Calendar.getInstance().getTime().getTime());
						date = Date.from(date.toInstant().plusSeconds(3600));
						
						mutePlayer(playerToMute, date);
					} catch (final ParseException e) {
						sender.sendMessage("§7[§aMute§7]§c " + e.getMessage());
					}
				else {
					@SuppressWarnings("deprecation")
					final OfflinePlayer player = Main.getInstance().getServer().getOfflinePlayer(args[0]);
					
					try {
						Date date = new Date(MUTE_PARSE_FORMAT_2.parse(args[1]).getTime() + Calendar.getInstance().getTime().getTime());
						date = Date.from(date.toInstant().plusSeconds(3600));
						
						mutePlayer(player.getUniqueId(), date);
					} catch (final ParseException e) {
						sender.sendMessage("§7[§aMute§7]§c " + e.getMessage());
					}
				}
			} else if (args.length == 1) {
				final Player playerToMute = Main.getInstance().getServer().getPlayer(args[0]);
				
				if (playerToMute != null) {
					if (isPlayerMuted(playerToMute)) {
						unmutePlayer(playerToMute);
						sender.sendMessage("§7[§aMute§7]§a Der Spieler " + args[0] + " wurde entmuted.");
					} else {
						mutePlayer(playerToMute, DATE_INFINITY);
						sender.sendMessage("§7[§aMute§7]§c Der Spieler " + args[0] + " ist jetzt gemuted.");
					}
				} else {
					@SuppressWarnings("deprecation")
					final OfflinePlayer player = Main.getInstance().getServer().getOfflinePlayer(args[0]);
					
					if (player != null) {
						final UUID uuid = player.getUniqueId();
						
						if (isPlayerMuted(uuid)) {
							unmutePlayer(uuid);
							sender.sendMessage("§7[§aMute§7]§a Der Spieler " + args[0] + " wurde entmuted.");
						} else {
							mutePlayer(uuid, DATE_INFINITY);
							sender.sendMessage("§7[§aMute§7]§c Der Spieler " + args[0] + " ist jetzt gemuted.");
						}
					} else
						sender.sendMessage("§7[§aMute§7]§c Der Spieler " + args[0] + " konnte nicht gefunden werden.");
				}
			} else 
				sender.sendMessage("§7[§aMute§7]§c /mute <player> <length>");
		} else
			Main.getInstance().sendMessageLang((Player) sender, "Mute", Dictionary.ADMIN_RESTRICTED);
		
		return false;
	}

	/**
	 * Muted einen Spieler
	 * @param player Spieler der gemuted wird
	 * @param date Zeitpunkt, an dem der Spieler wieder entmuted wird
	 */
	public static void mutePlayer(final Player player, final Date date) {
		MUTED_PLAYERS.put(player.getUniqueId(), date);
		
		player.sendMessage("§7[§aMute§7]§c§l Du wurdest gemuted!");
	}
	
	/**
	 * Muted einen Spieler
	 * @param player Spieler der gemuted wird
	 * @param date Zeitpunkt, an dem der Spieler wieder entmuted wird
	 */
	public static void mutePlayer(final UUID player, final Date date) {
		MUTED_PLAYERS.put(player, date);
	}
	
	/**
	 * Entmuted einen Spieler
	 * @param player Spieler der entmuted wird
	 */
	public static void unmutePlayer(final Player player) {
		MUTED_PLAYERS.remove(player.getUniqueId());
		
		player.sendMessage("§7[§aMute§7]§a Deine Mutezeit ist abgelaufen.");
	}

	/**
	 * Entmuted einen Spieler
	 * @param player Spieler der entmuted wird
	 */
	public static void unmutePlayer(final UUID player) {
		MUTED_PLAYERS.remove(player);
	}
	
	/**
	 * Gibt zurück bis wann der Spieler gemuted ist
	 * @param player Der gemutete Spieler
	 * @return Datum bis zum Entmutezeitpunkt
	 */
	public static Date playerMutedUntil(final Player player) {
		return MUTED_PLAYERS.get(player.getUniqueId());
	}
	
	/**
	 * Gibt zurück bis wann der Spieler gemuted ist
	 * @param player Der gemutete Spieler
	 * @return Datum bis zum Entmutezeitpunkt
	 */
	public static Date playerMutedUntil(final UUID player) {
		return MUTED_PLAYERS.get(player);
	}
	
	/**
	 * Ist der Spieler gemuted
	 * @param player Spieler der gechecked wird
	 * @return Ist der Spieler gemuted
	 */
	public static boolean isPlayerMuted(final Player player) {
		return MUTED_PLAYERS.containsKey(player.getUniqueId());
	}
	
	/**
	 * Ist der Spieler gemuted
	 * @param player Spieler der gechecked wird
	 * @return Ist der Spieler gemuted
	 */
	public static boolean isPlayerMuted(final UUID player) {
		return MUTED_PLAYERS.containsKey(player);
	}
}
