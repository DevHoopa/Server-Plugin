package de.flaflo.command;

import java.util.HashMap;

import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.flaflo.main.Main;

public class CommandFreeze implements CommandExecutor {

	public class FreezeTask implements Runnable {

		private boolean isRunning;

		private Player victim;
		private Location freezeBack;
		private GameMode gameModeBefore;

		public FreezeTask(Player p) {
			victim = p;
			freezeBack = p.getLocation();
			gameModeBefore = p.getGameMode();
		}

		/**
		 * Startet den Task
		 */
		public void start() {
			this.isRunning = true;
			this.run();
		}

		/**
		 * Stoppt den Task
		 */
		public void stop() {
			this.isRunning = false;
			
			new BukkitRunnable() {

				@Override
				public void run() {
					FreezeTask.this.victim.setGameMode(FreezeTask.this.gameModeBefore);
				}
			}.runTask(Main.getInstance());
		}

		@Override
		public void run() {
			new BukkitRunnable() {

				@Override
				public void run() {
					if (FreezeTask.this.isRunning) {
						Player vic = FreezeTask.this.victim;
						Location loc = FreezeTask.this.freezeBack;

						vic.teleport(loc);
						vic.setGameMode(GameMode.ADVENTURE);
						
						loc.getWorld().playEffect(loc, Effect.SNOWBALL_BREAK, 1);
					} else
						this.cancel();
				}

			}.runTaskTimer(Main.getInstance(), 0, 1L);
		}

		/**
		 * Gibt den Spieler, der sich nicht bewegen kann, zurück
		 * 
		 * @return
		 */
		public Player getVictim() {
			return victim;
		}

		/**
		 * Gibt den Teleportierungsort zurück
		 * 
		 * @return
		 */
		public Location getFreezeBack() {
			return freezeBack;
		}

		/**
		 * Setzt den Teleportierungsort
		 * 
		 * @param freezeBack
		 * @return
		 */
		public void setFreezeBack(Location freezeBack) {
			this.freezeBack = freezeBack;
		}

		/**
		 * Gibt zurück, ob der Task läuft
		 * 
		 * @return
		 */
		public boolean isRunning() {
			return isRunning;
		}

	}

	private HashMap<Player, FreezeTask> freezeTasks;

	public CommandFreeze() {
		freezeTasks = new HashMap<Player, FreezeTask>();
	}


	
	/**
	 * Friert einen Spieler an seiner Position ein
	 * 
	 * @param p
	 * @return
	 */
	private boolean freeze(Player p) {
		if (!freezeTasks.containsKey(p)) {
			FreezeTask ft = new FreezeTask(p);
			freezeTasks.put(p, ft);
			ft.start();

			return true;
		} else {
			FreezeTask ft = freezeTasks.get(p);
			ft.stop();
			freezeTasks.remove(p);

			return false;
		}
	}

	/**
	 * Friert einen Spieler an seiner Position ein
	 * 
	 * @param p
	 * @param freezed
	 * @return
	 */
	private boolean freeze(Player p, boolean freezed) {
		System.out.println(freezed);
		if (freezed) {
			FreezeTask ft = new FreezeTask(p);
			freezeTasks.put(p, ft);
			ft.start();
			
			
			return true;
		} else {
			FreezeTask ft = freezeTasks.get(p);
			ft.stop();
			freezeTasks.remove(p);
			
			return false;
		}
	}



	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		Player p = (Player) arg0;
		
		if (args.length == 0)
			p.sendMessage("§7[§aFreeze§7]§r Benutze §a/freeze <Spieler>§r oder §a/freeze <Spieler> <freezed>§r!");
		else if (args.length == 1) {
			Player target = Main.getInstance().getServer().getPlayer(args[0]);

			if (target == null) {
				p.sendMessage(("§7[§aFreeze§7]§r %player% §cwurde nicht gefunden!").replace("%player%", args[0]));
				return false;
			}
			
			if (target == p) {
				p.sendMessage(("§7[§aFreeze§7]§r §cDu kannst diesen Befehl nicht an dir selber benutzen!"));
				return false;
			}
			
			p.sendMessage((freeze(target) ? "§7[§aFreeze§7]§r %player% §akann sich nicht mehr bewegen." : "§7[§aFreeze§7]§r §r%player% §ckann sich wieder bewegen.").replace("%player%", target.getName()));
		} else if (args.length == 2) {
			Player target = Main.getInstance().getServer().getPlayer(args[0]);

			if (target == null) {
				p.sendMessage(("§7[§aFreeze§7]§r %player% §cwurde nicht gefunden!").replace("%player%", args[0]));
				return false;
			}
			
			if (target == p) {
				p.sendMessage(("§7[§aFreeze§7]§r §cDu kannst diesen Befehl nicht an dir selber benutzen!"));
				return false;
			}
			
			// Ja ja ich weiß, eigener parseBoolean :P
			if (args[1] == null || !args[1].equalsIgnoreCase("true") && !args[1].equalsIgnoreCase("false")) {
				p.sendMessage(("§7[§aFreeze§7]§r %value% §cist kein gültiger Wert!").replace("%value%", args[1]));
				return false;
			}

			boolean set = Boolean.parseBoolean(args[1]);

			p.sendMessage((freeze(target, set) ? "§7[§aFreeze§7]§r %player% §akann sich nicht mehr bewegen." : "§7[§aFreeze§7]§r §r%player% §ckann sich wieder bewegen.").replace("%player%", target.getName()));
		}

		return false;
	}

}
