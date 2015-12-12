package de.flaflo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;

/**
 * Zuständig für den Damage Befehl
 * 
 * @author Flaflo
 *
 */
public class CommandAFK implements CommandExecutor {

	public static class AFKTask implements Runnable {

		private HashMap<UUID, Location> lastChecked;
		private Thread checkThread;
		
		private boolean isRunning;
		
		public AFKTask() {
			lastChecked = new HashMap<UUID, Location>();
			checkThread = new Thread(this);
		}
		
		/**
		 * Started den task
		 */
		public void start() {
			this.isRunning = true;
			checkThread.start();
		}

		@Override
		public void run() {
			while (this.isRunning) {
				try {
					Thread.sleep(30000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	
				for (Player p : Main.getInstance().getServer().getOnlinePlayers()) {
					if (lastChecked.containsKey(p.getUniqueId())) {
						if (lastChecked.get(p.getUniqueId()).equals(p.getLocation()))
							if (!CommandAFK.getAfkPlayers().contains(p.getUniqueId()))
								CommandAFK.setAFK(p);

						lastChecked.replace(p.getUniqueId(), p.getLocation());
					} else
						lastChecked.put(p.getUniqueId(), p.getLocation());
				}
			}
		}
		
		/**
		 * Stopt den Task
		 */
		public void stop() {
			this.isRunning = false;
			lastChecked.clear();
			
			try {
				checkThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Gibt eine Map mit den letzten gecheckten Spielerids mit ihrer Position
		 * @return HashMap<UUID, Location>
		 */
		public HashMap<UUID, Location> getLastChecked() {
			return lastChecked;
		}

		/**
		 * Gibt den Checkthread zurück
		 * @return Thread
		 */
		public Thread getCheckThread() {
			return checkThread;
		}
		
		public boolean isRunning() {
			return isRunning;
		}
	}

	private static ArrayList<UUID> afkPlayers = new ArrayList<UUID>();
	private static AFKTask afkTask = new AFKTask();
	
	public CommandAFK() {
		afkTask.start();
	}
	
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
		if (!afkPlayers.contains(p.getUniqueId())) {
			afkPlayers.add(p.getUniqueId());
			
			Main.getInstance().getServer().broadcastMessage("§7[§aAFK§7]§r §e" + p.getName() + "§r ist jetzt AFK.");
		}
	}
	
	/**
	 * Lässt den Spieler vom AFK zurückkehren
	 * @param p
	 */
	public static void unsetAFK(Player p) {
		if (afkPlayers.contains(p.getUniqueId())) {
			afkPlayers.remove(p.getUniqueId());
			
			Main.getInstance().getServer().broadcastMessage("§7[§aAFK§7]§r §e" + p.getName() + "§r ist wieder zurück.");
		}
	}
	
	public static ArrayList<UUID> getAfkPlayers() {
		return afkPlayers;
	}

	/**
	 * @return the afkTask
	 */
	public static AFKTask getAfkTask() {
		return afkTask;
	}
	
}
