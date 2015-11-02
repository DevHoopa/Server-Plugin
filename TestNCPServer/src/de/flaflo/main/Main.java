package de.flaflo.main;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import de.flaflo.command.CommandConsole;
import de.flaflo.command.CommandDamage;
import de.flaflo.command.CommandFly;
import de.flaflo.command.CommandHeal;
import de.flaflo.command.CommandHunger;
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
		
		startClearLag();
	}
	
	/**
	 * Startet den ClearLag Loop
	 */
	private void startClearLag() {
		new BukkitRunnable() {

			@Override
			public void run() {
				Main.this.getServer().broadcastMessage("§7[§aClearLag§7]§r §e60§r Sekunden bevor alle Items auf dem Boden entfernt werden.");
				
				new BukkitRunnable() {

					@Override
					public void run() {
						Main.this.getServer().broadcastMessage("§7[§aClearLag§7]§r Entferne alle Items auf dem Boden...");
						
						for (Entity e : Main.this.getServer().getWorlds().get(0).getEntities()) {
							if (e instanceof Item)
								e.remove();
						}
					}
					
				}.runTaskLater(Main.this, 60 * 20);
			}
			
		}.runTaskTimer(this, 0, (60 * 8) * 20);
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
		this.getCommand("hunger").setExecutor(new CommandHunger());
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
