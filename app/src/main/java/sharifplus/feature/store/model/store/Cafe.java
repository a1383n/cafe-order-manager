package sharifplus.feature.store.model.store;

import sharifplus.core.utils.Pair;
import sharifplus.feature.store.model.Store;
import sharifplus.feature.store.model.products.Dessert;
import sharifplus.feature.store.model.products.Drink;

import java.util.List;

public class Cafe extends Store {
    @Override
    public List<Pair<String, List<Pair<String, List<String>>>>> getMenu() {
        return List.of(
                new Pair<>("Drinks", List.of(new Pair<>("Hot", Drink.Hot.getAll()), new Pair<>("Cold", Drink.Cold.getAll()))),
                new Pair<>("Desserts", List.of(new Pair<>("All Desserts", Dessert.getAll())))
        );
    }
}