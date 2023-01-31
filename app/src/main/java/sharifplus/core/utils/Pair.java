package sharifplus.core.utils;

/**
 * This class Hold two object with specified type. Call it Pair
 *
 * @param key   The object of A or key
 * @param value The object of B or value
 * @param <K>   The type of A or KEY
 * @param <V>   The type of B or value
 */
public record Pair<K, V>(K key, V value) {
}
