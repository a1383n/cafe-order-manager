package sharifplus.feature.store.model;

import sharifplus.core.utils.Pair;

import java.util.List;

/**
 * The store class
 */
public abstract class Store {
    /**
     * Get menu
     *
     * @return Ex.
     * [
     * "string": [
     * "string": [
     * "string"
     * ]
     * ]
     * ]
     */
    public abstract List<Pair<String, List<Pair<String, List<String>>>>> getMenu();
}
