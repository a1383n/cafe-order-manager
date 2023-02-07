package sharifplus.feature.store.model.products;

import sharifplus.feature.store.model.Ingredient;
import sharifplus.feature.store.model.Ingredients;
import sharifplus.feature.store.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dessert extends Product {
    public static final Map<String, List<Ingredient>> list = new HashMap<>() {
        {
            put("Chocolate cake", List.of(new Ingredient(Ingredients.FLOUR, 3), new Ingredient(Ingredients.CHOCOLATE, 2)));
            put("Vanilla cake", List.of(new Ingredient(Ingredients.FLOUR, 3), new Ingredient(Ingredients.VANILLA, 2)));
            put("Ice cream", List.of(new Ingredient(Ingredients.ICE_CREAM, 2)));
        }
    };

    public Dessert(String title) {
        super("dessert." + title, list.get(title));
    }

    public static List<String> getAll() {
        return list.keySet().stream().map(s -> "dessert." + s).toList();
    }
}
