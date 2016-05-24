package de.flaflo.main;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.flaflo.command.CommandAFK;
import de.flaflo.command.CommandClear;
import de.flaflo.command.CommandConsole;
import de.flaflo.command.CommandDamage;
import de.flaflo.command.CommandFly;
import de.flaflo.command.CommandFreebuild;
import de.flaflo.command.CommandHeal;
import de.flaflo.command.CommandHunger;
import de.flaflo.command.CommandItem;
import de.flaflo.command.CommandLang;
import de.flaflo.command.CommandMute;
import de.flaflo.command.CommandOPApple;
import de.flaflo.command.CommandPing;
import de.flaflo.command.CommandSpawn;
import de.flaflo.command.CommandTPA;
import de.flaflo.command.CommandWarp;
import de.flaflo.command.CommandWhois;
import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.listener.MainListener;
import de.flaflo.settings.Settings;
import de.flaflo.util.UMisc;

/**
 * Hauptklasse für das Serverplugin
 * 
 * @author Flaflo
 */
public class Main extends JavaPlugin {
	
	public static final boolean NOCHEAT_PLUS = false;

	private static Main main;
	private Settings settings;

	public Main() {
		main = this;
	}

	@Override
	public void onEnable() {
		settings = new Settings();

		//registerCommands();
		registerEvents();

		addAllInputs();
		startClearLag();
		startResetFreebuild();
		
		/**
		 * 
		 *  Registriert Befehle ohne die plugin.yml
		 *  @author UnderCreepe
		 * 
		 */
		
		try {
			   final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			   bukkitCommandMap.setAccessible(true);
			   CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

			   commandMap.register("item", new CommandItem());
			   commandMap.register("afk", new CommandAFK());
			   commandMap.register("hunger", new CommandHunger());
			   commandMap.register("heal", new CommandHeal());
			   commandMap.register("clear", new CommandClear());
			   commandMap.register("c", new CommandConsole());
			   commandMap.register("tpa", new CommandTPA());
			   commandMap.register("warp", new CommandWarp());
			   commandMap.register("spawn", new CommandSpawn());
			   commandMap.register("apple", new CommandOPApple());
			   commandMap.register("fly", new CommandFly());
			   commandMap.register("freebuild", new CommandFreebuild());
			   commandMap.register("ping", new CommandPing());
			   commandMap.register("whois", new CommandWhois());
			   commandMap.register("mute", new CommandMute());
			   commandMap.register("lang", new CommandLang());
			   commandMap.register("damage", new CommandDamage());
			   
			   
		} catch(Exception e) {
			   e.printStackTrace();
			}
		
	}

	/**
	 * Fügt alle zu ihren inputs hinzu
	 */
	private void addAllInputs() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (final Player p : Main.this.getServer().getOnlinePlayers()) {
					if (NOCHEAT_PLUS)
						p.chat("/testncp input " + p.getName());
				
					LanguageManager.getInstance().setCurrentLang(p, LanguageManager.getInstance().getLanguages()[0]);
				}
			}

		}.runTaskLater(this, 20L);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				Main.getInstance().broadcastMessageLang("Donate", Dictionary.DONATE1);
				Main.getInstance().broadcastMessageLang("Donate", Dictionary.DONATE2);
				Main.getInstance().broadcastMessageLang("Donate", Dictionary.DONATE3);
			}

		}.runTaskTimer(this, 0, (60 * 8) * 20);
	}

	/**
	 * Startet den ClearLag Loop
	 */
	private void startClearLag() {
		new BukkitRunnable() {

			@Override
			public void run() {
				Main.getInstance().broadcastMessageLang("Items", Dictionary.CLEAR_LAG_WARN, new ArgumentPair("secs", "10"));

				new BukkitRunnable() {

					@Override
					public void run() {
						UMisc.clearLag();
					}

				}.runTaskLater(Main.this, 5 * 20);
			}

		}.runTaskTimer(this, 0, (60 * 10) * 20);
	}

	/**
	 * Startet den Freebuild Loop
	 */
	private void startResetFreebuild() {
		new BukkitRunnable() {

			@Override
			public void run() {
				Main.getInstance().broadcastMessageLang("Freebuild", Dictionary.CLEAR_LAG_WARN, new ArgumentPair("secs", "60"));

				new BukkitRunnable() {

					@Override
					public void run() {
						UMisc.resetFreebuild();
					}

				}.runTaskLater(Main.this, 60 * 20);
			}

		}.runTaskTimer(this, 0, (60 * 20) * 20);
	}

	/**
	 * Registriert alle Listener
	 */
	private void registerEvents() {
		this.getServer().getPluginManager().registerEvents(new MainListener(), this);
	}

	/**
	 * Registeriert alle Commands
	 */
	/*private void registerCommands() {
	}*/
	
	/**
	 * Sendet eine Nachricht an einen Spieler
	 * @param player der Spieler
	 * @param prefix der Prefix
	 * @param dict die Dictionary
	 * @param pairs die Argumentenpaare
	 */
	public synchronized void sendMessageLang(final Player player, final String prefix, final Dictionary dict, final ArgumentPair... pairs) {
		String msg = "§7[§a" + prefix + "§7]§r " + LanguageManager.getInstance().getCurrentLang(player).getString(dict);
		
		for (final ArgumentPair pair : pairs)
			msg = msg.replace("%" + pair.getKey() +  "%", pair.getValue());
		
		player.sendMessage(msg);
	}
	
	/**
	 * Sendet eine Nachricht an einen Spieler
	 * @param player der Spieler
	 * @param prefix der Prefix
	 * @param dict die Dictionary
	 */
	public synchronized void sendMessageLang(final Player player, final String prefix, final Dictionary dict) {
		player.sendMessage("§7[§a" + prefix + "§7]§r " + LanguageManager.getInstance().getCurrentLang(player).getString(dict));
	}

	public synchronized void broadcastMessageLang(final String prefix, final Dictionary dict, final ArgumentPair... pairs) {
		Main.getInstance().getServer().getOnlinePlayers().forEach(pl -> sendMessageLang(pl, prefix, dict, pairs));
	}
	
	public synchronized void broadcastMessageLang(final String prefix, final Dictionary dict) {
		Main.getInstance().getServer().getOnlinePlayers().forEach(pl -> sendMessageLang(pl, prefix, dict));
	}
	
	/**
	 * Gibt die Momentane Instanz zurück
	 * 
	 * @return die Main Instanz
	 */
	public static Main getInstance() {
		return main;
	}

	/**
	 * Gibt das WorldGuard Plugin als dessen Instanz zurück
	 * 
	 * @return das WorldGuard Plugin
	 */
	public WorldGuardPlugin getWorldGuard() {
		final Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

		if ((plugin == null) || !(plugin instanceof WorldGuardPlugin))
			return null;

		return (WorldGuardPlugin) plugin;
	}

	/**
	 * Gibt die Settings Instanz zurück
	 * 
	 * @return Settings
	 */
	public Settings getSettings() {
		return settings;
	}
}
