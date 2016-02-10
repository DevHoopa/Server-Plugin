package de.flaflo.command;

import java.util.ArrayList;
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
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Zuständig für den Blocks Befehl
 * 
 * @author Flaflo
 */
public class CommandItem implements CommandExecutor {

	public static final ArrayList<Material> RESTRICTED_MATERIALS = new ArrayList<Material>() {
		private static final long serialVersionUID = -8474898387097438744L;
		{
			add(Material.MINECART);
			add(Material.COMMAND_MINECART);
			add(Material.EXPLOSIVE_MINECART);
			add(Material.HOPPER_MINECART);
			add(Material.POWERED_MINECART);
			add(Material.STORAGE_MINECART);
			add(Material.ARMOR_STAND);
			add(Material.BOAT);
			add(Material.BARRIER);
			add(Material.BEDROCK);
			add(Material.DRAGON_EGG);
		}
	};

	public static final Map<Enchantment, Integer> SUPER_AXE_ECHANTMENTS;
	public static final Map<Enchantment, Integer> SUPER_SHOVEL_ECHANTMENTS;

	static {
		SUPER_AXE_ECHANTMENTS = new HashMap<Enchantment, Integer>();
		SUPER_SHOVEL_ECHANTMENTS = new HashMap<Enchantment, Integer>();

		final ItemStack axeDummy = new ItemStack(Material.DIAMOND_PICKAXE);
		final ItemStack shovelDummy = new ItemStack(Material.DIAMOND_SPADE);

		for (Enchantment ench : Enchantment.values()) {
			if (ench.canEnchantItem(axeDummy))
				SUPER_AXE_ECHANTMENTS.put(ench, ench.getMaxLevel());
			if (ench.canEnchantItem(shovelDummy))
				SUPER_SHOVEL_ECHANTMENTS.put(ench, ench.getMaxLevel());
		}
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
					material = Material.getMaterial(args[0].toUpperCase());
				}

				if (material == null)
					p.sendMessage("§7[§aItem§7]§r §cDieser Block konnte nicht gefunden werden!");
				else {
					if (!RESTRICTED_MATERIALS.contains(material)) {
						amount = Integer.parseInt(args[1]);
	
						p.getInventory().addItem(new ItemStack(material, amount));
	
						p.sendMessage("§7[§aItem§7]§r Du hast §7" + amount + " " + WordUtils.capitalizeFully(material.name().replace("_", " ")) + "§r erhalten.");
					} else
						p.sendMessage("§7[§aItem§7]§c Dieses Item ist verboten!");
				}
			} catch (NumberFormatException ex) {
				p.sendMessage("§7[§aItem§7]§r §cDu musst eine Zahl angeben!");
			}
		} else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("superaxe")) {
				ItemStack superAxe = new ItemStack(Material.DIAMOND_PICKAXE);
				ItemMeta axeMeta = superAxe.getItemMeta();
				axeMeta.setDisplayName("§b§lSuper Spitzhacke");
				superAxe.setItemMeta(axeMeta);

				superAxe.addEnchantments(SUPER_AXE_ECHANTMENTS);

				p.getInventory().addItem(superAxe);
				p.sendMessage("§7[§aItem§7]§r §aDiese Spitzhacke bewirkt Wunder!");
			} else if (args[0].equalsIgnoreCase("supershovel")) {
				ItemStack superShovel = new ItemStack(Material.DIAMOND_SPADE);

				ItemMeta shovelMeta = superShovel.getItemMeta();
				shovelMeta.setDisplayName("§b§lSuper Schaufel");
				superShovel.setItemMeta(shovelMeta);

				superShovel.addEnchantments(SUPER_SHOVEL_ECHANTMENTS);

				p.getInventory().addItem(superShovel);
				p.sendMessage("§7[§aItem§7]§r §aDiese Schaufel bewirkt Wunder!");
			} else {
				Material material = null;

				try {
					material = Material.getMaterial(Integer.parseInt(args[0]));
				} catch (NumberFormatException ex) {
					material = Material.getMaterial(args[0].toUpperCase());
				}

				if (material == null)
					p.sendMessage("§7[§aItem§7]§r §cDieser Block konnte nicht gefunden werden!");
				else {
					if (!RESTRICTED_MATERIALS.contains(material)) {
						p.getInventory().addItem(new ItemStack(material, amount));

						p.sendMessage("§7[§aItem§7]§r Du hast §7" + amount + " " + WordUtils.capitalizeFully(material.name().replace("_", " ")) + "§r erhalten.");
					} else
						p.sendMessage("§7[§aItem§7]§c Dieses Item ist verboten!");
				}
			}
		} else {
			p.sendMessage("§7[§aItem§7]§c /item <id/name>");
			p.sendMessage("§7[§aItem§7]§c /item <id/name> <anzahl>");
		}

		return false;
	}

}
