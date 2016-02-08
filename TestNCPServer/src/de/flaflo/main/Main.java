package de.flaflo.main;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import de.flaflo.command.CommandAFK;
import de.flaflo.command.CommandItem;
import de.flaflo.command.CommandConsole;
import de.flaflo.command.CommandDamage;
import de.flaflo.command.CommandFly;
import de.flaflo.command.CommandFreeze;
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

		addAllInputs();
		startClearLag();
		startResetFreebuild();
	}
	
	/**
	 * Fügt alle zu ihren inputs hinzu
	 */
	private void addAllInputs() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player p : Main.this.getServer().getOnlinePlayers())
					p.chat("/testncp input " + p.getName());				
			}
			
		}.runTaskLater(this, 20L);
	}
	
	/**
	 * Startet den ClearLag Loop
	 */
	private void startClearLag() {
		new BukkitRunnable() {

			@Override
			public void run() {
				Main.this.getServer().broadcastMessage("§7[§aClearLag§7]§r §e10§r Sekunden bevor alle Items auf dem Boden entfernt werden.");
				
				new BukkitRunnable() {

					@Override
					public void run() {
						Main.this.getServer().broadcastMessage("§7[§aClearLag§7]§r Entferne alle Items auf dem Boden...");
						
						for (Entity e : Main.this.getServer().getWorlds().get(0).getEntities()) {
							if (e instanceof Item)
								e.remove();
						}
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
				Main.this.getServer().broadcastMessage("§7[§aFreebuild§7]§r §e60§r Sekunden bevor der Freebuild zurückgesetzt wird.");
				
				new BukkitRunnable() {

					@Override
					public void run() {
						Main.this.getServer().broadcastMessage("§7[§aFreebuild§7]§r Setze den Frebuild zurück...");
						
						CuboidSelection sel = new CuboidSelection(Main.this.getServer().getWorlds().get(0), new Location(Main.this.getServer().getWorlds().get(0), 780, 0, 150), new Location(Main.this.getServer().getWorlds().get(0), 761, 255, 131));
						try {
							Region region = sel.getRegionSelector().getRegion();
							region.getWorld().regenerate(region, WorldEdit.getInstance().getEditSessionFactory().getEditSession(region.getWorld(), -1));							
						} catch (IncompleteRegionException e) {
							e.printStackTrace();
						}
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
		this.getCommand("afk").setExecutor(new CommandAFK());
		this.getCommand("freeze").setExecutor(new CommandFreeze());
		this.getCommand("item").setExecutor(new CommandItem());
		this.getCommand("clear").setExecutor(new CommandItem());
	}
	
	/**
	 * Gibt die Momentane Instanz zurück
	 * @return die Main Instanz
	 */
	public static Main getInstance() {
		return main;
	}
	
	/**
	 * Gibt das WorldGuard Plugin als dessen Instanz zurück
	 * @return das WorldGuard Plugin
	 */
	public WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

	    if (plugin == null || !(plugin instanceof WorldGuardPlugin))
	        return null;

	    return (WorldGuardPlugin) plugin;
	}
	
	/**
	 * Gibt die Settings Instanz zurück
	 * @return Settings
	 */
	public Settings getSettings() {
		return settings;
	}
}
