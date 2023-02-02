package sharifplus.feature.store.model.products;

import sharifplus.feature.store.model.Ingredient;
import sharifplus.feature.store.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Appetizer extends Product {
    public static final Map<String, List<Ingredient>> list = new HashMap<>() {
        {
            put("Salad", List.of(new Ingredient(Ingredient.Ingredients.VEGETABLE, 3)));
            put("French fries", List.of(new Ingredient(Ingredient.Ingredients.POTATO, 3)));
        }
    };

    public Appetizer(String title) {
        super("appetizer." + title,list.get(title));
    }

    public static List<String> getAll() {
        return list.keySet().stream().toList();
    }
}
