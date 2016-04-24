package de.flaflo.util;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;
import net.minecraft.server.v1_8_R2.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R2.EntityHuman;
import net.minecraft.server.v1_8_R2.EntityPlayer;
import net.minecraft.server.v1_8_R2.World;

/**
 * Allgeimeine Utilities für den Spieler
 * 
 * @author Flaflo
 */
public class UPlayer {
	
	public static final CopyOnWriteArrayList<Player> TELEPORTING_PLAYERS = new CopyOnWriteArrayList<Player>();
	
	/**
	 * Sendet Chunkupdates an einen Spieler
	 */
	public static void sendChunkUpdates() {
		int xDiff, yDiff;
		int viewDistance = Bukkit.getServer().getViewDistance() << 4;
		
		for (Chunk chunk : Bukkit.getWorlds().get(0).getLoadedChunks()) {
			World world = ((org.bukkit.craftbukkit.v1_8_R2.CraftChunk) chunk).getHandle().world;
			
		    for (EntityHuman eh : world.players) {
		    	if (eh instanceof EntityPlayer) {
			        xDiff = Math.abs((int) eh.locX - chunk.getX() << 4);
			        yDiff = Math.abs((int) eh.locZ - chunk.getZ() << 4);
			        
			        if (xDiff <= viewDistance && yDiff <= viewDistance)
			            ((EntityPlayer) eh).chunkCoordIntPairQueue.add(new ChunkCoordIntPair(chunk.getX(), chunk.getZ()));
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
		TELEPORTING_PLAYERS.add(p);
		
		new Delay() {

			@Override
			public void run() {
				p.teleport(loc);
		
				TELEPORTING_PLAYERS.remove(p);
			}

		}.start(delay * 1000L);
	}

	/**
	 * Teleportiert den Spieler zum gewünschten Spieler
	 * 
	 * @param p1 Spieler 1
	 * @param p2 Spieler 2
	 * @param delay Verzögerung
	 */
	public static void teleport(Player p1, Player p2, long delay) {
		TELEPORTING_PLAYERS.add(p1);
		
		new Delay() {
			@Override
			public void run() {
				p1.teleport(p2.getLocation());
				
				TELEPORTING_PLAYERS.remove(p1);
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
		p.sendMessage("§7[§aSpawn§7]§r Teleportiere zum Spawn...");
		
		teleport(p, Main.getInstance().getSettings().getSpawn(), delayed ? 2L: 0L);
	}

	/**
	 * Sucht und gibt einen Spieler anhand seiner UUID zurück
	 * @param uuid UUID des gesuchten Spielers
	 * @return Den gesuchten Spieler
	 */
	public static Player getPlayerByUUID(UUID uuid) {
		for (Player player : Main.getInstance().getServer().getOnlinePlayers())
			if (player.getUniqueId().equals(uuid))
				return player;
		
		return null;
	}
}
