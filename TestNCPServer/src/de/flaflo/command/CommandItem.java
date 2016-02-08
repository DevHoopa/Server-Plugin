package de.flaflo.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Zuständig für den Blocks Befehl
 * 
 * @author Flaflo
 */
public class CommandItem implements CommandExecutor {

	public static final Map<Enchantment, Integer> ALL_ENCHANTMENTS;
	
	static {
		ALL_ENCHANTMENTS = new HashMap<Enchantment, Integer>();
		
		for (Enchantment ench : Enchantment.values())
			ALL_ENCHANTMENTS.put(ench, ench.getMaxLevel());
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		Player p = (Player) arg0;

		int amount = 1;

		if (args.length == 2) {
			try {
				Material material = null;

				try {
					material = Material.getMaterial(Integer.parseInt(args[0]));
				} catch (NumberFormatException ex) {
					material = Material.getMaterial(args[0]);
				}

				if (material == null)
					p.sendMessage("§7[§aItem§7]§r §cDieser Block konnte nicht gefunden werden!");
				else {
					amount = Integer.parseInt(args[1]);

					p.getInventory().addItem(new ItemStack(material, amount));

					p.sendMessage("§7[§aItem§7]§r Du hast §7" + amount + " " + WordUtils.capitalizeFully(material.name().replace("_", " ")) + "§r erhalten.");
				}
			} catch (NumberFormatException ex) {
				p.sendMessage("§7[§aItem§7]§r §cDu musst eine Zahl angeben!");
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("superaxe")) {
				ItemStack superAxe = new ItemStack(Material.DIAMOND_PICKAXE, amount);
				superAxe.addEnchantments(ALL_ENCHANTMENTS);
			} else {
				Material material = null;

				try {
					material = Material.getMaterial(Integer.parseInt(args[0]));
				} catch (NumberFormatException ex) {
					material = Material.getMaterial(args[0]);
				}

				if (material == null)
					p.sendMessage("§7[§aItem§7]§r §cDieser Block konnte nicht gefunden werden!");
				else {
					p.getInventory().addItem(new ItemStack(material, amount));

					p.sendMessage("§7[§aItem§7]§r Du hast §7" + amount + " " + WordUtils.capitalizeFully(material.name().replace("_", " ")) + "§r erhalten.");
				}
			}
		} else {
			p.sendMessage("§7[§aItem§7]§r §c/item <id/name>");
			p.sendMessage("§7[§aItem§7]§r §c/item <id/name> <anzahl>");
		}

		return false;
	}

}
