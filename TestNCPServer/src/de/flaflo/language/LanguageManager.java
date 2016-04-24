package de.flaflo.language;

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

		FREEBUILD_PROGRESS("freebuild.progress"),
		FREEBUILD_SUCCESS("freebuild.success"),

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
		MUTE_FINISH("heal.success"),

		APPLE_ONE("apple.one"),
		APPLE_MORE("apple.more"),

		PING_INFO("ping.info"),
		
		SPAWN_INFO("spawn.info"),
		SPAWN_SET("spawn.set");

		String dict;
		
		private Dictionary(String dict) {
			this.dict = dict;
		}
		
		@Override
		public String toString() {
			return dict;
		}
	}
	
	private Language[] languages = {
			new Language("en") {
				@Override
				public void setup() {
					
				}},
			new Language("de") {
				@Override
				public void setup() {
					this.addString(Dictionary.PLAYER_NOT_FOUND, "Dieser Spieler wurde nicht gefunden.");
					this.addString(Dictionary.ADMIN_RESTRICTED, "Du besitzt nicht genügend Rechte.");

					this.addString(Dictionary.CHAT_MUTE_PERM, "Du bist PERMANENT gemuted!");
					this.addString(Dictionary.CHAT_MUTE_UNTIL, "Du bist noch bis %until% gemuted!");
					
					this.addString(Dictionary.AFK_TRUE, "§e%player%§r ist jetzt AFK.");
					this.addString(Dictionary.AFK_FALSE, "§e%player%§r ist wieder zurück.");

					this.addString(Dictionary.CLEAR_SUCCESS, "Dein Inventar wurde geleert.");
					this.addString(Dictionary.CLEAR_ADMIN_SUCCESS, "%players%s Inventar wurde geleert.");

					this.addString(Dictionary.DISPATCH_SUCCESS, "Command erfolgreich ausgeführt.");
					this.addString(Dictionary.DISPATCH_ERROR, "Es ist ein Fehler aufgetreten.");

					this.addString(Dictionary.FLY_ON, "Du fliegst jetzt.");
					this.addString(Dictionary.FLY_OFF, "Du fliegst jetzt nicht mehr.");

					this.addString(Dictionary.FREEBUILD_PROGRESS, "Setze den Freebuild zurück...");
					this.addString(Dictionary.FREEBUILD_SUCCESS, "Freebuild erfolgreich zurückgesetzt.");

					this.addString(Dictionary.HEAL_SUCCESS, "Du wurdest geheilt.");

					this.addString(Dictionary.HUNGER_ON, "Du erlaubst nun Hunger.");
					this.addString(Dictionary.HUNGER_OFF, "Du erlaubst Hunger nicht mehr.");

					this.addString(Dictionary.ITEM_FORMAT, "Du musst eine Zahl angeben.");
					this.addString(Dictionary.ITEM_INVALID, "Dieses Item konnte nicht gefunden werden.");
					this.addString(Dictionary.ITEM_RESTRICTED, "Dieses Item ist verboten!");
					this.addString(Dictionary.ITEM_SUCCESS, "Du hast §7%amount% %item%§r erhalten.");
					this.addString(Dictionary.ITEM_SUPERAXE, "Diese Spitzhacke bewirkt Wunder!");
					this.addString(Dictionary.ITEM_SUPERSPADE, "Diese Schaufel bewirkt Wunder!");

					this.addString(Dictionary.MUTE_TRUE, "Der Spieler %player% wurde gemuted.");
					this.addString(Dictionary.MUTE_FALSE, "Der Spieler %player% wurde entmuted.");
					this.addString(Dictionary.MUTE_FINISH, "Deine Mutezeit ist abgelaufen.");

					this.addString(Dictionary.APPLE_ONE, "Du hast §b%amount% Apfel§r erhalten.");
					this.addString(Dictionary.APPLE_MORE, "Du hast §b%amount% Äpfel§r erhalten.");

					this.addString(Dictionary.PING_INFO, "Dein Ping ist: %ping%");
				}
			}};
	
	private Language currentLang;

	/**
	 * @return the currentLang
	 */
	public Language getCurrentLang() {
		return currentLang;
	}

	/**
	 * @param currentLang the currentLang to set
	 */
	public void setCurrentLang(Language currentLang) {
		this.currentLang = currentLang;
	}

	/**
	 * @return the languages
	 */
	public Language[] getLanguages() {
		return languages;
	}
	
	public static LanguageManager getInstance() {
		return inst;
	}
}
