package de.flaflo.language;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class LanguageManager {

	private static LanguageManager inst = new LanguageManager();
	
	public static enum Dictionary {
		PLAYER_NOT_FOUND("player.not.found"),
		ADMIN_RESTRICTED("admin.restrict"),
		
		CHAT_MUTE_PERM("chat.mute.perm"),
		CHAT_MUTE_UNTIL("chat.mute.until"),
		
		AFK_TRUE("afk.true"),
		AFK_FALSE("afk.false"),
		
		CLEAR_SUCCESS("clear.success"),
		CLEAR_ADMIN_SUCCESS("clear.admin.success"),
		
		DISPATCH_SUCCESS("dispatch.success"),
		DISPATCH_ERROR("dispatch.error"),

		DAMAGE_ON("damage.on"),
		DAMAGE_OFF("damage.off"),

		FLY_ON("fly.on"),
		FLY_OFF("fly.off"),

		CLEAR_LAG_WARN("clearlag.warn"),
		CLEAR_LAG_SUCCESS("clearlag.success"),

		HEAL_SUCCESS("heal.success"),
		
		HUNGER_ON("hunger.on"),
		HUNGER_OFF("hunger.off"),
		
		ITEM_INVALID("item.invalid"),
		ITEM_SUCCESS("item.success"),
		ITEM_FORMAT("item.format"),
		ITEM_RESTRICTED("item.restrict"),
		ITEM_SUPERAXE("item.superaxe"),
		ITEM_SUPERSPADE("item.superspade"),
		
		MUTE_TRUE("mute.true"),
		MUTE_FALSE("mute.false"),
		MUTE_FINISH("mute.finish"),

		APPLE_ONE("apple.one"),
		APPLE_MORE("apple.more"),

		PING_INFO("ping.info"),
		
		SPAWN_INFO("spawn.info"),
		SPAWN_SET("spawn.set"),
		
		TPA_INFO1("tpa.info1"),
		TPA_INFO2("tpa.info2"),
		TPA_SENT("tpa.sent"),
		TPA_INFO3("tpa.info3"),
		TPA_INFO4("tpa.info4"),
		TPA_SUCCESS("tpa.success"),
		TPA_NO_REQUESTS("tpa.no.requests"),
		TPA_ERROR("tpa.error"),
		
		WARP_INFO1("warp.info1"),
		WARP_INFO2("warp.info2"),
		WARP_SUCCESS("warp.success"),
		WARP_NOT_EXIST("warp.not.exist"),
		WARP_SET("warp.set"),
		
		NO_FLY_PVP("pvp.fly.deny"),
		NO_HEAL_PVP("pvp.heal.deny"),
		
		DONATE1("donate.1"),
		DONATE2("donate.2"),
		DONATE3("donate.3");

		final String dict;
		
		private Dictionary(final String dict) {
			this.dict = dict;
		}
		
		@Override
		public String toString() {
			return dict;
		}
	}
	
	private final Language[] languages = {
			new Language("en") {
				@Override
				public void setup() {
					this.addString(Dictionary.PLAYER_NOT_FOUND, "This player wasn't found.");
					this.addString(Dictionary.ADMIN_RESTRICTED, "You don't have enough permissions.");

					this.addString(Dictionary.CHAT_MUTE_PERM, "You were muted PERMANENTLY!");
					this.addString(Dictionary.CHAT_MUTE_UNTIL, "You're muted until %until%!");
					
					this.addString(Dictionary.AFK_TRUE, "%player% is now AFK.");
					this.addString(Dictionary.AFK_FALSE, "%player% is back.");

					this.addString(Dictionary.CLEAR_SUCCESS, "Your inventory has been cleared.");
					this.addString(Dictionary.CLEAR_ADMIN_SUCCESS, "%players%s inventory was cleared.");

					this.addString(Dictionary.DISPATCH_SUCCESS, "Command successfully executed.");
					this.addString(Dictionary.DISPATCH_ERROR, "An error has occured.");

					this.addString(Dictionary.DAMAGE_ON, "You're now allowing damage.");
					this.addString(Dictionary.DAMAGE_OFF, "You're now longer allowing damage.");
					
					this.addString(Dictionary.FLY_ON, "You're now flying.");
					this.addString(Dictionary.FLY_OFF, "You're no longer flying.");

					this.addString(Dictionary.CLEAR_LAG_WARN, "ClearLag starts in %secs% seconds.");
					this.addString(Dictionary.CLEAR_LAG_SUCCESS, "Successfully executed ClearLag.");

					this.addString(Dictionary.HEAL_SUCCESS, "You've been healed.");

					this.addString(Dictionary.HUNGER_ON, "You're now allowing hunger.");
					this.addString(Dictionary.HUNGER_OFF, "You're no longer allowing hunger.");

					this.addString(Dictionary.ITEM_FORMAT, "You've to provide a number.");
					this.addString(Dictionary.ITEM_INVALID, "This item wasn't found.");
					this.addString(Dictionary.ITEM_RESTRICTED, "This item is restricted!");
					this.addString(Dictionary.ITEM_SUCCESS, "You gained %amount% %item%.");
					this.addString(Dictionary.ITEM_SUPERAXE, "This pickaxe miracles!");
					this.addString(Dictionary.ITEM_SUPERSPADE, "This shovel miracles!");

					this.addString(Dictionary.MUTE_TRUE, "The Player %player% has been muted.");
					this.addString(Dictionary.MUTE_FALSE, "The Player %player% has been unmuted.");
					this.addString(Dictionary.MUTE_FINISH, "Your mute is over.");

					this.addString(Dictionary.APPLE_ONE, "You gained %amount% apple.");
					this.addString(Dictionary.APPLE_MORE, "You gained %amount% apples.");

					this.addString(Dictionary.PING_INFO, "Your ping is: %ping%");
					
					this.addString(Dictionary.SPAWN_INFO, "Teleporting to spawn...");
					this.addString(Dictionary.SPAWN_SET, "Spawn set.");
					
					this.addString(Dictionary.TPA_INFO1, "To to send a request type /tpa <name>.");
					this.addString(Dictionary.TPA_INFO2, "To accept a request type /tpa accept.");
					this.addString(Dictionary.TPA_SUCCESS, "Teleporting to %player%...");
					
					this.addString(Dictionary.TPA_SENT, "Request sent.");
					this.addString(Dictionary.TPA_INFO3, "%player% wants to teleport.");
					this.addString(Dictionary.TPA_INFO4, "Type /tpa accept to accept.");
					
					this.addString(Dictionary.TPA_NO_REQUESTS, "You don't have a request to accept.");
					this.addString(Dictionary.TPA_ERROR, "You can't teleport yourself!");
					
					this.addString(Dictionary.WARP_INFO1, "Use /warp <name> to teleport.");
					this.addString(Dictionary.WARP_INFO2, "Or use /warp list for a list of warps.");
					this.addString(Dictionary.WARP_SUCCESS, "Teleporting to %warp%...");
					this.addString(Dictionary.WARP_NOT_EXIST, "The warp %warp% doesn't exist.");
					this.addString(Dictionary.WARP_SET, "Warp %warp% set.");
					
					this.addString(Dictionary.NO_FLY_PVP, "You aren't allowed to fight while flying.");
					this.addString(Dictionary.NO_HEAL_PVP, "You aren't allowed to heal while flying.");
					
					this.addString(Dictionary.DONATE1, "You like the Servers?");
					this.addString(Dictionary.DONATE2, "Keep them alive and donate:");
					this.addString(Dictionary.DONATE3, "http://goo.gl/G7D4iu");
				}},
			new Language("de") {
				@Override
				public void setup() {
					this.addString(Dictionary.PLAYER_NOT_FOUND, "Dieser Spieler wurde nicht gefunden.");
					this.addString(Dictionary.ADMIN_RESTRICTED, "Du besitzt nicht genügend Rechte.");

					this.addString(Dictionary.CHAT_MUTE_PERM, "Du bist PERMANENT gemuted!");
					this.addString(Dictionary.CHAT_MUTE_UNTIL, "Du bist noch bis %until% gemuted!");
					
					this.addString(Dictionary.AFK_TRUE, "%player% ist jetzt AFK.");
					this.addString(Dictionary.AFK_FALSE, "%player% ist wieder zurück.");

					this.addString(Dictionary.CLEAR_SUCCESS, "Dein Inventar wurde geleert.");
					this.addString(Dictionary.CLEAR_ADMIN_SUCCESS, "%player%s Inventar wurde geleert.");

					this.addString(Dictionary.DISPATCH_SUCCESS, "Command erfolgreich ausgeführt.");
					this.addString(Dictionary.DISPATCH_ERROR, "Es ist ein Fehler aufgetreten.");
					
					this.addString(Dictionary.DAMAGE_ON, "Du erlaubst nun schaden.");
					this.addString(Dictionary.DAMAGE_OFF, "Du erlaubst keinen schaden mehr.");

					this.addString(Dictionary.FLY_ON, "Du fliegst jetzt.");
					this.addString(Dictionary.FLY_OFF, "Du fliegst jetzt nicht mehr.");

					this.addString(Dictionary.CLEAR_LAG_WARN, "ClearLag startet in %secs% Sekunden.");
					this.addString(Dictionary.CLEAR_LAG_SUCCESS, "ClearLag erfolgreich ausgeführt.");

					this.addString(Dictionary.HEAL_SUCCESS, "Du wurdest geheilt.");

					this.addString(Dictionary.HUNGER_ON, "Du erlaubst nun Hunger.");
					this.addString(Dictionary.HUNGER_OFF, "Du erlaubst Hunger nicht mehr.");

					this.addString(Dictionary.ITEM_FORMAT, "Du musst eine Zahl angeben.");
					this.addString(Dictionary.ITEM_INVALID, "Dieses Item konnte nicht gefunden werden.");
					this.addString(Dictionary.ITEM_RESTRICTED, "Dieses Item ist verboten!");
					this.addString(Dictionary.ITEM_SUCCESS, "Du hast %amount% %item% erhalten.");
					this.addString(Dictionary.ITEM_SUPERAXE, "Diese Spitzhacke bewirkt Wunder!");
					this.addString(Dictionary.ITEM_SUPERSPADE, "Diese Schaufel bewirkt Wunder!");

					this.addString(Dictionary.MUTE_TRUE, "Der Spieler %player% wurde gemuted.");
					this.addString(Dictionary.MUTE_FALSE, "Der Spieler %player% wurde entmuted.");
					this.addString(Dictionary.MUTE_FINISH, "Deine Mutezeit ist abgelaufen.");

					this.addString(Dictionary.APPLE_ONE, "Du hast %amount% Apfel erhalten.");
					this.addString(Dictionary.APPLE_MORE, "Du hast %amount% Äpfel erhalten.");

					this.addString(Dictionary.PING_INFO, "Dein Ping ist: %ping%");
					
					this.addString(Dictionary.SPAWN_INFO, "Teleportiere zum Spawn...");
					this.addString(Dictionary.SPAWN_SET, "Spawn gesetzt.");
					
					this.addString(Dictionary.TPA_INFO1, "Um eine Anfrage zu senden mache /tpa <name>.");
					this.addString(Dictionary.TPA_INFO2, "Um eine Anfrage anzunehmen mache /tpa accept.");
					this.addString(Dictionary.TPA_SUCCESS, "Teleportiere zu %player%...");
					this.addString(Dictionary.TPA_NO_REQUESTS, "Du hast keine Anfrage, die du akzeptieren könntest.");
					this.addString(Dictionary.TPA_ERROR, "Du kannst dich nicht zu dir selbst teleportieren!");
					
					this.addString(Dictionary.WARP_INFO1, "Benutze /warp <name> um dich zu teleportieren.");
					this.addString(Dictionary.WARP_INFO2, "Oder /warp list für eine Liste der Warps.");
					this.addString(Dictionary.WARP_SUCCESS, "Teleportiere zu %warp%...");
					this.addString(Dictionary.WARP_NOT_EXIST, "Der Warp %warp% existiert nicht.");
					this.addString(Dictionary.WARP_SET, "Warp %warp% gesetzt.");
					
					this.addString(Dictionary.NO_FLY_PVP, "Du darfst während dem Fliegen nicht kämpfen!");
					this.addString(Dictionary.NO_HEAL_PVP, "Du kannst dich während dem Kämpfen nicht heilen!");
					
					this.addString(Dictionary.DONATE1, "Du willst die Server weiterhin?");
					this.addString(Dictionary.DONATE2, "Dann spende unter folgendem Link:");
					this.addString(Dictionary.DONATE3, "http://goo.gl/G7D4iu");
				}
		}};
	
	private final HashMap<UUID, Language> currentLangs = new HashMap<UUID, Language>();

	/**
	 * @return the currentLang
	 */
	public synchronized Language getCurrentLang(final Player player) {
		return currentLangs.get(player.getUniqueId());
	}

	/**
	 * @param currentLang the currentLang to set
	 */
	public synchronized void setCurrentLang(final Player player, final Language currentLang) {
		if (!currentLangs.containsKey(player.getUniqueId()))
			currentLangs.put(player.getUniqueId(), currentLang);
		else
			currentLangs.replace(player.getUniqueId(), currentLang);
		
		player.sendMessage("§7[§aLanguage§7]§r §6Current Language switched to §l" + currentLang.getName().toUpperCase());
	}

	/**
	 * @return the languages
	 */
	public synchronized Language[] getLanguages() {
		return languages;
	}
	
	public synchronized static LanguageManager getInstance() {
		return inst;
	}
}
