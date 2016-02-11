package de.flaflo.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;

public class CommandClear implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		
		if (args.length == 0) {
			((Player) arg0).getInventory().clear();
			
			arg0.sendMessage("�7[�aClear�7]�r Dein Inventar wurde geleert.");
		} else if (args.length == 1) {
			if (arg0.isOp()) {
				if (args[0].equalsIgnoreCase("items")) {
					Main.getInstance().getServer().broadcastMessage("�7[�aClearLag�7]�r Entferne alle Items auf dem Boden...");
					
					for (Entity e : Main.getInstance().getServer().getWorlds().get(0).getEntities())
						if (e instanceof Item)
							e.remove();
				} else {
					Player player = Main.getInstance().getServer().getPlayer(args[0]);
					
					if (player != null) {
						player.getInventory().clear();
						
						arg0.sendMessage("�7[�aClear�7]�r " + player.getName() + " Inventar wurde geleert.");
					} else
						arg0.sendMessage("�7[�aClear�7]�c Dieser Spieler wurde nicht gefunden.");
				}
			} else
				arg0.sendMessage("�7[�aClear�7]�c Du besitzt nicht gen�gend Rechte!");
		} else
			arg0.sendMessage("�7[�aClear�7]�c /clear");
		
		return true;
	}

}
