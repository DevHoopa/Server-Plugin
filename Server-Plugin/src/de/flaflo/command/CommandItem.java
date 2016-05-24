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

import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

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
			add(Material.DISPENSER);
			add(Material.ANVIL);
			add(Material.MOB_SPAWNER);
			add(Material.ITEM_FRAME);
			add(Material.PAINTING);
		}
	};

	public static final Map<Enchantment, Integer> SUPER_AXE_ECHANTMENTS;
	public static final Map<Enchantment, Integer> SUPER_SHOVEL_ECHANTMENTS;

	static {
		SUPER_AXE_ECHANTMENTS = new HashMap<Enchantment, Integer>();
		SUPER_SHOVEL_ECHANTMENTS = new HashMap<Enchantment, Integer>();

		final ItemStack axeDummy = new ItemStack(Material.DIAMOND_PICKAXE);
		final ItemStack shovelDummy = new ItemStack(Material.DIAMOND_SPADE);

		for (final Enchantment ench : Enchantment.values()) {
			if (ench.canEnchantItem(axeDummy))
				SUPER_AXE_ECHANTMENTS.put(ench, ench.getMaxLevel());
			if (ench.canEnchantItem(shovelDummy))
				SUPER_SHOVEL_ECHANTMENTS.put(ench, ench.getMaxLevel());
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		final Player p = (Player) arg0;

		int amount = 1;

		if (args.length == 2)
			try {
				Material material = null;

				try {
					material = Material.getMaterial(Integer.parseInt(args[0]));
				} catch (final NumberFormatException ex) {
					material = Material.getMaterial(args[0].toUpperCase());
				}

				if (material == null)
					Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_INVALID);
				else if (!RESTRICTED_MATERIALS.contains(material)) {
					amount = Integer.parseInt(args[1]);

					p.getInventory().addItem(new ItemStack(material, amount));

					Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_SUCCESS, new ArgumentPair("amount", "§e" + amount), new ArgumentPair("item", "§7" + WordUtils.capitalizeFully(material.name().replace("_", " "))));
				} else
					Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_RESTRICTED);
			} catch (final NumberFormatException ex) {
				Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_FORMAT);
			}
		else if (args.length == 1) {
			if (args[0].equalsIgnoreCase("superaxe")) {
				final ItemStack superAxe = new ItemStack(Material.DIAMOND_PICKAXE);
				final ItemMeta axeMeta = superAxe.getItemMeta();
				axeMeta.setDisplayName("§b§lSuper Pickaxe");
				superAxe.setItemMeta(axeMeta);

				superAxe.addEnchantments(SUPER_AXE_ECHANTMENTS);

				p.getInventory().addItem(superAxe);
				Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_SUPERAXE);
			} else if (args[0].equalsIgnoreCase("supershovel")) {
				final ItemStack superShovel = new ItemStack(Material.DIAMOND_SPADE);

				final ItemMeta shovelMeta = superShovel.getItemMeta();
				shovelMeta.setDisplayName("§b§lSuper Shovel");
				superShovel.setItemMeta(shovelMeta);

				superShovel.addEnchantments(SUPER_SHOVEL_ECHANTMENTS);

				p.getInventory().addItem(superShovel);
				Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_SUPERSPADE);
			} else {
				Material material = null;

				try {
					material = Material.getMaterial(Integer.parseInt(args[0]));
				} catch (final NumberFormatException ex) {
					material = Material.getMaterial(args[0].toUpperCase());
				}

				if (material == null)
					Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_INVALID);
				else if (!RESTRICTED_MATERIALS.contains(material)) {
					p.getInventory().addItem(new ItemStack(material, amount));

					Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_SUCCESS, new ArgumentPair("amount", "§e" + amount), new ArgumentPair("item", "§7" + WordUtils.capitalizeFully(material.name().replace("_", " "))));
				} else
					Main.getInstance().sendMessageLang(p, "Item", Dictionary.ITEM_RESTRICTED);
			}
		} else {
			p.sendMessage("§7[§aItem§7]§c /item <id/name>");
			p.sendMessage("§7[§aItem§7]§c /item <id/name> <amount>");
		}

		return false;
	}

}
