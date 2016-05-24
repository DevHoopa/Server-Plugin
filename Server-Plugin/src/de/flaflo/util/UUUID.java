package de.flaflo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UUUID {
	
	private static final HashMap<String, String> CACHE = new HashMap<String, String>();

	public static String getUUID(String username) {
		if (CACHE.containsKey(username))
			return CACHE.get(username);
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
			InputStream stream = url.openStream();
			InputStreamReader inr = new InputStreamReader(stream);
			BufferedReader reader = new BufferedReader(inr);
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = reader.readLine()) != null) {
				sb.append(s);
			}
			String result = sb.toString();

			JsonElement element = new JsonParser().parse(result);
			JsonObject obj = element.getAsJsonObject();

			String uuid = obj.get("id").toString();

			uuid = uuid.substring(1);
			uuid = uuid.substring(0, uuid.length() - 1);

			CACHE.put(username, uuid);

			return uuid;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
