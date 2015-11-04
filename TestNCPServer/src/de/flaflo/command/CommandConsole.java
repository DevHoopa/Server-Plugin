package de.flaflo.command;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.flaflo.main.Main;

/**
 * Zuständig für den Spawn Befehl
 * 
 * @author Flaflo
 */
public class CommandConsole implements CommandExecutor {

	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] args) {
		final Player p = (Player) arg0;

		if (args.length == 0) {
			if (!p.isOp() || !p.hasPermission("server.command.dispatch"))
				p.sendMessage("§7[§aConsole§7]§r §cDu besitzt nicht genügend Rechte!");
			else
				p.sendMessage("§7[§aConsole§7]§r Benutze /c <commad>");
		} else if (args.length > 0) {
			if (p.isOp() || p.hasPermission("server.command.dispatch")) {

				StringBuilder cmdSb = new StringBuilder();

				for (int i = 0; i < args.length; i++) {
					String cmd = args[i];

					cmdSb.append(cmd);

					if (i < args.length - 1)
						cmdSb.append(" ");
				}
				
				String command = cmdSb.toString();

				PrintStream old = System.out;
				PrintStream cache = null;

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				try {
					cache = new PrintStream(baos, true, "utf-8");
				} catch (UnsupportedEncodingException e) {
				}

				if (cache != null) {
					System.setOut(cache);

					Main.getInstance().getServer().dispatchCommand(Main.getInstance().getServer().getConsoleSender(), command);

					String response = baos.toString();
					
					if (!response.isEmpty())
						p.sendMessage("§7[§aConsole§7]§r " + baos.toString());
					else
						p.sendMessage("§7[§aConsole§7]§r Command erfolgreich ausgeführt");
						
					System.setOut(old);
				} else
					p.sendMessage("§7[§aConsole§7]§r §cEs ist ein Fehler aufgetreten!");
			} else
				p.sendMessage("§7[§aConsole§7]§r §cDu besitzt nicht genügend Rechte!");
		}

		return false;
	}

}
