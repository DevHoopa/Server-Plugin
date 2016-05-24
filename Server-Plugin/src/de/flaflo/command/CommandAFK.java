package de.flaflo.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.language.ArgumentPair;
import de.flaflo.language.LanguageManager.Dictionary;
import de.flaflo.main.Main;

/**
 * Zuständig für den Damage Befehl
 * 
 * @author Flaflo
 */
public class CommandAFK implements CommandExecutor {

	public class AFKTask implements Runnable {

		private final HashMap<UUID, Location> lastChecked;
		private final Thread checkThread;

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
					Thread.sleep(300000L);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}

				for (final Player p : Main.getInstance().getServer().getOnlinePlayers())
					if (lastChecked.containsKey(p.getUniqueId())) {
						if (lastChecked.get(p.getUniqueId()).equals(p.getLocation()))
							CommandAFK.setAFK(p);

						lastChecked.remove(p.getUniqueId());
					} else {
						lastChecked.put(p.getUniqueId(), p.getLocation());

						if (!CommandAFK.getAfkPlayers().contains(p.getUniqueId()))
							CommandAFK.setAFK(p);
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
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Gibt eine Map mit den letzten gecheckten Spielerids mit ihrer
		 * Position
		 * 
		 * @return HashMap<UUID, Location>
		 */
		public HashMap<UUID, Location> getLastChecked() {
			return lastChecked;
		}

		/**
		 * Gibt den Checkthread zurück
		 * 
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
	private final AFKTask afkTask = new AFKTask();

	public CommandAFK() {
//		afkTask.start();
	}

	@Override
	public boolean onCommand(final CommandSender arg0, final Command arg1, final String arg2, final String[] args) {
		if (args.length == 0) {
			final Player p = (Player) arg0;

			if (!afkPlayers.contains(p.getUniqueId()))
				setAFK(p);
			else
				unsetAFK(p);
		}

		return false;
	}

	/**
	 * Setzt einen Spieler AFK
	 * 
	 * @param p
	 */
	public static void setAFK(final Player p) {
		if (!afkPlayers.contains(p.getUniqueId())) {
			afkPlayers.add(p.getUniqueId());

			Main.getInstance().broadcastMessageLang("AFK", Dictionary.AFK_TRUE, new ArgumentPair("player", p.getName()));
		}
	}

	/**
	 * Lässt den Spieler vom AFK zurückkehren
	 * 
	 * @param p
	 */
	public static void unsetAFK(final Player p) {
		if (afkPlayers.contains(p.getUniqueId())) {
			afkPlayers.remove(p.getUniqueId());
			
			Main.getInstance().broadcastMessageLang("AFK", Dictionary.AFK_FALSE, new ArgumentPair("player", p.getName()));
		}
	}

	public static ArrayList<UUID> getAfkPlayers() {
		return afkPlayers;
	}

	/**
	 * @return the afkTask
	 */
	public AFKTask getAfkTask() {
		return afkTask;
	}

}
