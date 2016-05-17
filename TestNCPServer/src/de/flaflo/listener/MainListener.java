package de.flaflo.listener;

import org.bukkit.Material;
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
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import de.flaflo.command.CommandAFK;
import de.flaflo.command.CommandDamage;
import de.flaflo.command.CommandFly;
import de.flaflo.command.CommandHeal;
import de.flaflo.command.CommandHunger;
import de.flaflo.command.CommandMute;
import de.flaflo.language.LanguageManager;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;
import de.flaflo.util.UPlayer;
import net.md_5.bungee.api.ChatColor;

/**
 * Hauptklasse für alle Listener
 * (Ich hasse dich, Flaflo, warum nur EINE klasse?)
 * 
 * @author Flaflo
 */
public class MainListener implements Listener {

	@EventHandler
	private void onPlayerMove(final PlayerMoveEvent e) {
		if (e.getFrom().distance(e.getTo()) > 0.15) {
			final Player p = e.getPlayer();

			if (CommandAFK.getAfkPlayers().contains(p.getUniqueId()))
				CommandAFK.unsetAFK(p);
		}
	}

	@EventHandler
	private void onItemConsume(final PlayerItemConsumeEvent e) {
		if(e.getItem().getType() == Material.GOLDEN_APPLE) {
			//DONT FUCKING EAT NOTCHIAN APPLES
			if(e.getItem().getData().getData() == 1) {
				e.getPlayer().setHealth(0);
				e.getPlayer().getInventory().clear();
			}
		}
	}
	
	@EventHandler
	private void onFoodLevelChanged(final FoodLevelChangeEvent e) {
		if (e.getEntity() instanceof Player)
			if (CommandHunger.getPlayersBlockedHunger().contains(e.getEntity().getUniqueId()))
				e.setCancelled(true);
	}

	@EventHandler
	private void onCreatureSpawn(final CreatureSpawnEvent e) {
		if (e.getEntityType().equals(EntityType.WITHER) || e.getEntityType().equals(EntityType.ARMOR_STAND) || e.getEntityType().equals(EntityType.IRON_GOLEM) || e.getEntityType().equals(EntityType.SNOWMAN))
			e.setCancelled(true);

		if (e.getSpawnReason().equals(SpawnReason.NATURAL))
			e.setCancelled(true);
	}

	@EventHandler
	private void onDamage(final EntityDamageEvent e) {
		if (e.getEntity() instanceof Player)
			if (!CommandDamage.getDamageablePlayers().contains(e.getEntity().getUniqueId()))
				e.setCancelled(true);

		if (e.getCause().equals(DamageCause.POISON) || e.getCause().equals(DamageCause.MAGIC))
			e.setCancelled(false);
	}

	@EventHandler
	private void onDamageByEntity(final EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (!CommandDamage.getDamageablePlayers().contains(e.getEntity().getUniqueId()) || !CommandDamage.getDamageablePlayers().contains(e.getDamager().getUniqueId()))
				e.setCancelled(true);
			
			if((e.getCause() == DamageCause.ENTITY_ATTACK) || (e.getCause() == DamageCause.PROJECTILE)) {
				if(e.getDamager() instanceof Player) {
					final Player damager = (Player) e.getDamager();
					
					if(CommandFly.getPlayersallowedflying().contains(damager.getUniqueId())) {
						Main.getInstance().sendMessageLang(damager, "PvP", Dictionary.NO_FLY_PVP);
						e.setCancelled(true);
					}
					
				} else {
					CommandHeal.getLastFightByPlayer().put((Player) e.getDamager(), System.currentTimeMillis());
					CommandHeal.getLastFightByPlayer().put((Player) e.getEntity(), System.currentTimeMillis());
				}
				
			}
			
		}
	}

	@EventHandler
	private void onJoin(final PlayerJoinEvent e) {
		e.getPlayer().chat("/testncp input " + e.getPlayer().getName());
		if (LanguageManager.getInstance().getCurrentLang(e.getPlayer()) == null) {
			e.getPlayer().sendMessage("§7[§aLanguage§7]§6 Welcome, to choose a language type /lang <en/de>");
			LanguageManager.getInstance().setCurrentLang(e.getPlayer(), LanguageManager.getInstance().getLanguages()[0]);
		}
		UPlayer.spawn(e.getPlayer(), false);
	}

	@EventHandler
	private void onLeave(final PlayerQuitEvent e) {
		if (CommandAFK.getAfkPlayers().contains(e.getPlayer().getUniqueId()))
			CommandAFK.getAfkPlayers().remove(e.getPlayer().getUniqueId());
	}

	@EventHandler
	private void onChat(final AsyncPlayerChatEvent e) {
		final Player player = e.getPlayer();

		e.setCancelled(true);

		if (CommandMute.isPlayerMuted(player))
			player.sendMessage("§7[§aMute§7]§c Du bist " + (CommandMute.playerMutedUntil(player) == CommandMute.DATE_INFINITY ? "PERMANENT" : "noch bis " + CommandMute.MUTE_PARSE_FORMAT_1.format(CommandMute.playerMutedUntil(player))) + " gemuted!");
		else
			Main.getInstance().getServer().broadcastMessage(ChatColor.GRAY + "<" + (player.isOp() || player.hasPermission("chat.admin") ? ChatColor.RED + "[Admin] " + ChatColor.RESET : ChatColor.GREEN + "[Tester] " + ChatColor.RESET) + player.getName() + ChatColor.GRAY + "> " + ChatColor.RESET + e.getMessage());
	}

}
