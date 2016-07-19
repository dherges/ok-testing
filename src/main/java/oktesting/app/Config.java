package oktesting.app;

import java.util.HashMap;
import java.util.Map;


/** A very simple way to configure our application */
public final class Config {

    private static final Map<String, Object> VALUES = new HashMap<>();

    /**
     * Sets a configuration value.
     *
     * @param key Configuration key
     * @param value Configuration value
     * @param <T> Type of value
     */
    public static <T> void configSet(String key, T value) {
        VALUES.put(key, value);
    }

    /**
     * Reads a configuration value.
     *
     * @param key Configuration key
     * @param defValue A default value that is returned, when the key is not present
     * @param <T> Type of value
     * @return The configuration value
     */
    @SuppressWarnings("unchecked")
    public static <T> T config(String key, T defValue) {
        return (T) VALUES.getOrDefault(key, defValue);
    }
}
