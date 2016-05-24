package de.flaflo.settings;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.flaflo.main.Main;

/**
 * Alle Einstellungen für das Plugin
 * 
 * @author Flaflo
 *
 */
public class Settings {
	
	private static File cfgFile = new File("plugins/Server", "settings.yml");
	private static FileConfiguration config = YamlConfiguration.loadConfiguration(cfgFile);
	
	public Settings() {
		if (!cfgFile.exists())
			createConfig();
	}
	
	/**
	 * Erstellt eine Standard Konfiguration
	 */
	private void createConfig() {
		config.set("world", Main.getInstance().getServer().getWorlds().get(0).getName());
		config.set("spawn.x", 0);
		config.set("spawn.y", 0);
		config.set("spawn.z", 0);
		config.set("spawn.yaw", 0);
		config.set("spawn.pitch", 0);
		
		this.save();
	}
	
	/**
	 * Setzt den Spawnpunkt
	 * @param spawn
	 */
	public void setSpawn(final Location spawn) {
		config.set("spawn.world", spawn.getWorld().getName());
		config.set("spawn.x", spawn.getX());
		config.set("spawn.y", spawn.getY());
		config.set("spawn.z", spawn.getZ());
		config.set("spawn.yaw", spawn.getYaw());
		config.set("spawn.pitch", spawn.getPitch());
		
		this.save();
	}
	
	/**
	 * Gibt einen Warppunkt zurück
	 * 
	 * @return Location
	 */
	public Location getWarp(final String name) {
		if (config.getString("warps." + name) == null || config.getString("warps." + name).isEmpty())
			return null;
		
		final Location l = new Location(Bukkit.getWorld(config.getString("warps." + name + ".world")), config.getDouble("warps." + name + ".x"), config.getDouble("warps." + name + ".y"), config.getDouble("warps." + name + ".z"));
		
		l.setYaw((float) config.getDouble("warps." + name + ".yaw"));
		l.setPitch((float) config.getDouble("warps." + name + ".pitch"));
		
		return l;
	}
	
	/**
	 * Gibt eine Liste der existierenden Warps aus
	 * @return List<String>
	 */
	public Set<String> getWarps() {
		return config.getConfigurationSection("warps").getKeys(false);
	}
	
	/**
	 * Setzt einen Warppunkt
	 * @param warp, name
	 */
	public void setWarp(final Location warp, final String name) {
		config.set("warps." + name + ".world", warp.getWorld().getName());
		config.set("warps." + name + ".x", warp.getX());
		config.set("warps." + name + ".y", warp.getY());
		config.set("warps." + name + ".z", warp.getZ());
		config.set("warps." + name + ".yaw", warp.getYaw());
		config.set("warps." + name + ".pitch", warp.getPitch());
		
		this.save();
	}
	
	public void removeWarp(final String name) {
		config.set("warps." + name, null);
		
		this.save();
	}
	
	/**
	 * Gibt den Spawnpunkt zurück
	 * 
	 * @return Location
	 */
	public Location getSpawn() {
		return new Location(Bukkit.getWorlds().get(0), -738, 4, 1681, 88, -2);
	}
	
	/**
	 * Speichert die Einstellungen
	 */
	public void save() {
		try {
			config.save(cfgFile);
		} catch (final IOException e) {
			Main.getInstance().getLogger().log(Level.WARNING, "Fehler beim speichern der Einstellungen: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Gibt das YML Konfigurations Objekt zurück
	 * @return FileConfiguration
	 */
	public FileConfiguration getConfig() {
		return config;
	}
}
