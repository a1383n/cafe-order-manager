package sharifplus.feature.store.model.products;

import sharifplus.feature.store.model.Ingredient;
import sharifplus.feature.store.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Food extends Product {
    public static final Map<String, List<Ingredient>> list = new HashMap<>() {
        {
            put("Pizza", List.of(new Ingredient(Ingredient.Ingredients.FLOUR, 2), new Ingredient(Ingredient.Ingredients.EGG, 2)));
            put("Berger", List.of(new Ingredient(Ingredient.Ingredients.MEAT, 2), new Ingredient(Ingredient.Ingredients.BREAD, 3)));
            put("Steak", List.of(new Ingredient(Ingredient.Ingredients.MEAT, 4)));
            put("Fried Chicken", List.of(new Ingredient(Ingredient.Ingredients.CHICKEN, 1), new Ingredient(Ingredient.Ingredients.EGG, 2)));
        }
    };

    public Food(String title) {
        super("food." + title,list.get(title));
    }

    public static List<String> getAll() {
        return list.keySet().stream().toList();
    }
}
