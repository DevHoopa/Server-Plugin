package de.flaflo.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;
import com.sk89q.worldedit.regions.Region;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

/**
 * Verschiedene Utilities
 * 
 * @author Flaflo
 *
 */
public class UMisc {

	/**
	 * Gibt den Key anhand der Value aus einer HashMap aus
	 * 
	 * @param map
	 * @param value
	 * @return
	 */
	public static <T, E> T getKeyByValue(final Map<T, E> map, final E value) {
	    for (final Entry<T, E> entry : map.entrySet())
	        if (Objects.equals(value, entry.getValue()))
	            return entry.getKey();
	    
	    return null;
	}
	
	private static final CuboidSelection FREEBUILD_SELECTION = new CuboidSelection(Bukkit.getServer().getWorlds().get(0), new Location(Bukkit.getWorlds().get(0), 780, 0, 150), new Location(Bukkit.getServer().getWorlds().get(0), 761, 255, 131));
	private static final Region FREEBUILD_REGION = createRegionFromCuboidSelection(FREEBUILD_SELECTION);
	
	/**
	 * Setzt den Freebuild
	 */
	public static void resetFreebuild() {
		final Iterator<BlockVector> freeBuildBlocks = FREEBUILD_REGION.iterator();

		while (freeBuildBlocks.hasNext()) {
			final BlockVector vec = freeBuildBlocks.next();
			final Block block = FREEBUILD_SELECTION.getWorld().getBlockAt(new Location(FREEBUILD_SELECTION.getWorld(), vec.getBlockX(), vec.getBlockY(), vec.getBlockZ()));

			if (vec.getBlockY() <= 0)
				block.setType(Material.BEDROCK);
			else if (vec.getBlockY() <= 2)
				block.setType(Material.DIRT);
			else if (vec.getBlockY() == 3)
				block.setType(Material.GRASS);
			else
				block.setType(Material.AIR);
		}
		
		UPlayer.sendChunkUpdates();
		
		clearLag();
	}
	
	public static void clearLag() {
		for (final Entity e : Main.getInstance().getServer().getWorlds().get(0).getEntities())
			if (e instanceof Item)
				e.remove();

		Main.getInstance().broadcastMessageLang("ClearLag", Dictionary.CLEAR_LAG_SUCCESS);
	}
	
	private static Region createRegionFromCuboidSelection(final CuboidSelection sel) {
		try {
			return sel.getRegionSelector().getRegion();
		} catch (final IncompleteRegionException e) {
			e.printStackTrace();

			return null;
		}
	}
}
