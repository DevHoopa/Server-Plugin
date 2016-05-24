package de.flaflo.command;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

/**
 * Zuständig für den Heal Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandHeal extends Command {

	private static final HashMap<Player, Long> lastFightByPlayer = new HashMap<Player, Long>();
	
	public CommandHeal()
	{
		super("heal");
	}
	
	@Override
	public boolean execute(final CommandSender arg0, final String arg2, final String[] args) {
		if (args.length == 0) {
			final Player p = (Player) arg0;

			if(lastFightByPlayer.get(p) == null || System.currentTimeMillis() - lastFightByPlayer.get(p) > 1000 * 15) {
				p.setHealth(p.getMaxHealth());
				p.setFoodLevel(20);
				p.setSaturation(20);
				
				p.removePotionEffect(PotionEffectType.BLINDNESS);
				p.removePotionEffect(PotionEffectType.CONFUSION);
				p.removePotionEffect(PotionEffectType.HUNGER);
				p.removePotionEffect(PotionEffectType.POISON);
				p.removePotionEffect(PotionEffectType.SLOW);
				p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
				p.removePotionEffect(PotionEffectType.WEAKNESS);
				p.removePotionEffect(PotionEffectType.WITHER);
				p.setFireTicks(0);
				
				Main.getInstance().sendMessageLang(p, "Heal", Dictionary.HEAL_SUCCESS);
			} else {
				Main.getInstance().sendMessageLang(p, "PvP", Dictionary.NO_HEAL_PVP);
			}
		}

		return false;
	}

	public static HashMap<Player, Long> getLastFightByPlayer() {
		return lastFightByPlayer;
	}

}
