package de.flaflo.command;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;

/**
 * Zust�ndig f�r den Damage Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandAFK implements CommandExecutor {

	private static ArrayList<UUID> afkPlayers = new ArrayList<UUID>();
	
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		if (args.length == 0) {
			Player p = (Player) arg0;

			if (!afkPlayers.contains(p.getUniqueId()))
				setAFK(p);
			else
				unsetAFK(p);
		}
		
		return false;
	}

	/**
	 * Setzt einen Spieler AFK
	 * @param p
	 */
	public static void setAFK(Player p) {
		afkPlayers.add(p.getUniqueId());
		
		Main.getInstance().getServer().broadcastMessage("�7[�aAFK�7]�r �e" + p.getName() + "�r ist jetzt AFK.");
	}
	
	/**
	 * L�sst den Spieler vom AFK zur�ckkehren
	 * @param p
	 */
	public static void unsetAFK(Player p) {
		afkPlayers.remove(p.getUniqueId());
		
		Main.getInstance().getServer().broadcastMessage("�7[�aAFK�7]�r �e" + p.getName() + "�r ist wieder zur�ck.");
	}
	
	public static ArrayList<UUID> getAfkPlayers() {
		return afkPlayers;
	}
	
}
