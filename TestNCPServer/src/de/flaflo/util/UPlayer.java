package de.flaflo.util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.flaflo.main.Main;

/**
 * Allgeimeine Utilities für den Spieler
 * 
 * @author Flaflo
 *
 */
public class UPlayer {

	/**
	 * Teleportiert den Spieler an die gewüschnte Position
	 * 
	 * @param p
	 * @param loc
	 * @param delay
	 */
	public static void teleport(Player p, Location loc, long delay) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p.teleport(loc);
			}
			
		}.runTaskLater(Main.getInstance(), delay * 20L);
	}
	
	/**
	 * Teleportiert den Spieler zum gewünschten Spieler
	 * 
	 * @param p1
	 * @param p2
	 * @param delay
	 */
	public static void teleport(Player p1, Player p2, long delay) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				p1.teleport(p2.getLocation());
			}
			
		}.runTaskLater(Main.getInstance(), delay * 20L);
	}
	
	
	/**
	 * Teleportiert den Spieler an den gesetzten Spawn
	 * 
	 * @param p
	 */
	public static void spawn(Player p) {
		teleport(p, Main.getInstance().getSettings().getSpawn(), 2L);
	}

}
