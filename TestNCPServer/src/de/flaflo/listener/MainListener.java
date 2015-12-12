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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.flaflo.command.CommandAFK;
import de.flaflo.command.CommandDamage;
import de.flaflo.command.CommandHunger;
import de.flaflo.util.UPlayer;

/**
 * Hauptklasse für alle Listener
 * 
 * @author Flaflo
 */
public class MainListener implements Listener {

	@EventHandler
	private void onPlayerMove(PlayerMoveEvent e) {
		if (e.getFrom().distance(e.getTo()) > 0.15) {
			Player p = e.getPlayer();
			
			if (CommandAFK.getAfkPlayers().contains(p.getUniqueId()))
				CommandAFK.unsetAFK(p);
		}
	}
	
	@EventHandler
	private void onFoodLevelChanged(FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player)
			if (CommandHunger.getPlayersBlockedHunger().contains(e.getEntity().getUniqueId()))
				e.setCancelled(true);
	}

	@EventHandler
	private void onCreatureSpawn(CreatureSpawnEvent e) {
		if (!e.getSpawnReason().equals(SpawnReason.EGG) && !e.getSpawnReason().equals(SpawnReason.CUSTOM) && !e.getSpawnReason().equals(SpawnReason.SPAWNER))
			e.setCancelled(true);
	}

	@EventHandler
	private void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player)
			if (!CommandDamage.getDamageablePlayers().contains(e.getEntity().getUniqueId()))
				e.setCancelled(true);
		
		if (e.getCause().equals(DamageCause.POISON) || e.getCause().equals(DamageCause.MAGIC))
			e.setCancelled(false);
	}

	@EventHandler
	private void onDamageByEntity(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player)
			if (!CommandDamage.getDamageablePlayers().contains(e.getEntity().getUniqueId()) || !CommandDamage.getDamageablePlayers().contains(e.getDamager().getUniqueId()))
				e.setCancelled(true);
	}

	@EventHandler
	private void onBlockBreak(BlockBreakEvent e) {
		if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			e.setCancelled(true);
	}

	@EventHandler
	private void onBlockPlace(BlockPlaceEvent e) {
		if (!e.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			e.setCancelled(true);
	}

	@EventHandler
	private void onJoin(PlayerJoinEvent e) {
		UPlayer.spawn(e.getPlayer(), false);
		e.getPlayer().chat("/testncp input " + e.getPlayer().getName());
	}
	
	@EventHandler
	private void onLeave(PlayerQuitEvent e) {
		if (CommandAFK.getAfkPlayers().contains(e.getPlayer().getUniqueId()))
			CommandAFK.getAfkPlayers().remove(e.getPlayer().getUniqueId());
	}

}
