package de.flaflo.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Verschiedene Utilities
 * 
 * @author Flaflo
 *
 */
public class UMisc {

	/**
	 * Gibt den Key anhand der Value aus einer HashMap aus
	 * 
	 * @param map
	 * @param value
	 * @return
	 */
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
	    for (Entry<T, E> entry : map.entrySet())
	        if (Objects.equals(value, entry.getValue()))
	            return entry.getKey();
	    
	    return null;
	}
}
