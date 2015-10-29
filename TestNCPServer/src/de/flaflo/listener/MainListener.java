package de.flaflo.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import de.flaflo.command.CommandDamage;
import de.flaflo.util.UPlayer;

/**
 * Hauptklasse für alle Listener
 * 
 * @author Flaflo
 *
 */
public class MainListener implements Listener {

	@EventHandler
	private void onCreatureSpawn(CreatureSpawnEvent e) {
		if (e.getSpawnReason() == SpawnReason.EGG || e.getSpawnReason() == SpawnReason.CUSTOM)
			e.setCancelled(false);
		else
			e.setCancelled(true);
	}

	@EventHandler
	private void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();

			if (!CommandDamage.damage.contains(p.getUniqueId()))
				e.setCancelled(true);
		}
	}

	@EventHandler
	private void onDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (!CommandDamage.damage.contains(e.getEntity().getUniqueId()) || !CommandDamage.damage.contains(e.getDamager().getUniqueId()))
				e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
			e.setCancelled(true);
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		UPlayer.spawn(e.getPlayer());
	}

}
