package de.flaflo.util;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;
//TODO Fix this
import net.minecraft.server.v1_8_R2.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityPlayer;

/**
 * Allgeimeine Utilities für den Spieler
 * 
 * @author Flaflo
 */
public class UPlayer {
	
	/**
	 * Sender Chunkupdates an einen Spieler
	 */
	public static void sendChunkUpdates() {
		int xDiff, yDiff;
		int viewDistance = Bukkit.getServer().getViewDistance() << 4;
		
		for (Chunk chunk : Bukkit.getWorlds().get(0).getLoadedChunks()) {
			net.minecraft.server.v1_8_R2.World world = ((org.bukkit.craftbukkit.v1_8_R2.CraftChunk) chunk).getHandle().world;
			
		    for (EntityHuman eh : world.players) {
		    	if (eh instanceof EntityPlayer) {
		    		EntityPlayer ep = (EntityPlayer) eh;
		    		
			        xDiff = Math.abs((int) ep.locX - chunk.getX() << 4);
			        yDiff = Math.abs((int) ep.locZ - chunk.getZ() << 4);
			        
			        if (xDiff <= viewDistance && yDiff <= viewDistance)
			            ep.chunkCoordIntPairQueue.add(new ChunkCoordIntPair(chunk.getX(), chunk.getZ()));
		    	}
		    }
		}
	}
	
	/**
	 * Teleportiert den Spieler an die gewüschnte Position
	 * 
	 * @param p
	 * @param loc
	 * @param delay
	 */
	public static void teleport(Player p, Location loc, long delay) {
		new Delay() {

			@Override
			public void run() {
				p.teleport(loc);
			}

		}.start(delay * 1000L);
	}

	/**
	 * Teleportiert den Spieler zum gewünschten Spieler
	 * 
	 * @param p1
	 * @param p2
	 * @param delay
	 */
	public static void teleport(Player p1, Player p2, long delay) {
		new Delay() {
			@Override
			public void run() {
				p1.teleport(p2.getLocation());
			}

		}.start(delay * 1000L);
	}

	/**
	 * Teleportiert den Spieler an den gesetzten Spawn
	 * 
	 * @param p
	 * @param delayed
	 */
	public static void spawn(Player p, boolean delayed) {
		teleport(p, Main.getInstance().getSettings().getSpawn(), delayed ? 2L: 0L);
	}

}
