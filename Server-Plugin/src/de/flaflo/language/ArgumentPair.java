package de.flaflo.language;

public final class ArgumentPair {
	
	private final String key;
	private final String value;

	public ArgumentPair(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

	public final String getKey() {
		return key;
	}

	public final String getValue() {
		return value;
	}
}
