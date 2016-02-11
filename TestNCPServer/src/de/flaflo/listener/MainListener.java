package de.flaflo.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.flaflo.command.CommandAFK;
import de.flaflo.command.CommandDamage;
import de.flaflo.command.CommandHunger;
import de.flaflo.main.Main;
import de.flaflo.util.UPlayer;
import net.md_5.bungee.api.ChatColor;

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
		if (e.getEntityType().equals(EntityType.WITHER) || e.getEntityType().equals(EntityType.ARMOR_STAND) || e.getEntityType().equals(EntityType.IRON_GOLEM) ||  e.getEntityType().equals(EntityType.SNOWMAN))
			e.setCancelled(true);
		
		if (e.getSpawnReason().equals(SpawnReason.NATURAL))
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
	private void onJoin(PlayerJoinEvent e) {
		UPlayer.spawn(e.getPlayer(), false);
		e.getPlayer().chat("/testncp input " + e.getPlayer().getName());
	}
	
	@EventHandler
	private void onLeave(PlayerQuitEvent e) {
		if (CommandAFK.getAfkPlayers().contains(e.getPlayer().getUniqueId()))
			CommandAFK.getAfkPlayers().remove(e.getPlayer().getUniqueId());
	}
	
	@EventHandler
	private void onChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();

		e.setCancelled(true);
		
		Main.getInstance().getServer().broadcastMessage(ChatColor.GRAY + "<" + (player.isOp() ? ChatColor.RED + "[Admin] " + ChatColor.RESET : ChatColor.GREEN + "[Tester] " + ChatColor.RESET) + player.getName() +  ChatColor.GRAY + "> " + ChatColor.RESET + e.getMessage());
	}

}
