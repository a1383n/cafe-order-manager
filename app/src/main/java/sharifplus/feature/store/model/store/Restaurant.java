package sharifplus.feature.store.model.store;

import sharifplus.core.utils.Pair;
import sharifplus.feature.store.model.Store;
import sharifplus.feature.store.model.products.Appetizer;
import sharifplus.feature.store.model.products.Food;

import java.util.List;

public class Restaurant extends Store {
    @Override
    public List<Pair<String, List<Pair<String, List<String>>>>> getMenu() {
        return List.of(
                new Pair<>("Food", List.of(new Pair<>("Main dishes", Food.getAll()), new Pair<>("Appetizers", Appetizer.getAll())))
        );
    }
}
