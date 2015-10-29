package de.flaflo.main;

import org.bukkit.plugin.java.JavaPlugin;

import de.flaflo.command.CommandConsole;
import de.flaflo.command.CommandDamage;
import de.flaflo.command.CommandFly;
import de.flaflo.command.CommandHeal;
import de.flaflo.command.CommandOPApple;
import de.flaflo.command.CommandPing;
import de.flaflo.command.CommandSpawn;
import de.flaflo.command.CommandTPA;
import de.flaflo.command.CommandWarp;
import de.flaflo.listener.MainListener;
import de.flaflo.settings.Settings;

/**
 * Hauptklasse für das Serverplugin
 * 
 * @author Flaflo
 *
 */
public class Main extends JavaPlugin {

	private static Main main;
	private Settings settings;
	
	public Main() {
		main = this;
	}
	
	@Override
	public void onEnable() {
		settings = new Settings();
		
		registerCommands();
		registerEvents();
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
	private void registerCommands() {
		this.getCommand("ping").setExecutor(new CommandPing());
		this.getCommand("fly").setExecutor(new CommandFly());
		this.getCommand("spawn").setExecutor(new CommandSpawn());
		this.getCommand("damage").setExecutor(new CommandDamage());
		this.getCommand("heal").setExecutor(new CommandHeal());
		this.getCommand("apple").setExecutor(new CommandOPApple());
		this.getCommand("warp").setExecutor(new CommandWarp());
		this.getCommand("tpa").setExecutor(new CommandTPA());
		this.getCommand("c").setExecutor(new CommandConsole());
	}
	
	/**
	 * Gibt die Momentane Instanz zurück
	 * @return Main
	 */
	public static Main getInstance() {
		return main;
	}
	
	/**
	 * Gibt die Settings Instanz zurück
	 * @return Settings
	 */
	public Settings getSettings() {
		return settings;
	}
}
