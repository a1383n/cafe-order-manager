package sharifplus.feature.store.model.products;

import sharifplus.feature.store.model.Ingredient;
import sharifplus.feature.store.model.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Drink {
    public static class Hot extends Product {
        public static final Map<String, List<Ingredient>> list = new HashMap<>() {
            {
                put("Coffee", List.of(new Ingredient(Ingredient.Ingredients.COFFEE, 2), new Ingredient(Ingredient.Ingredients.WATER, 1)));
                put("Tea", List.of(new Ingredient(Ingredient.Ingredients.TEA, 2), new Ingredient(Ingredient.Ingredients.WATER, 1)));
                put("Hot chocolate", List.of(new Ingredient(Ingredient.Ingredients.CHOCOLATE, 2), new Ingredient(Ingredient.Ingredients.WATER, 1)));
            }
        };

        public Hot(String title) {
            this.title = "drink.hot." + title;
            this.ingredientList = list.get(title);
        }

        public static List<String> getAll() {
            return list.keySet().stream().toList();
        }
    }

    public static class Cold extends Product {
        public static final Map<String, List<Ingredient>> list = new HashMap<>() {
            {
                put("Soda", List.of(new Ingredient(Ingredient.Ingredients.SODA, 2)));
                put("Water", List.of(new Ingredient(Ingredient.Ingredients.WATER, 2)));
            }
        };

        public Cold() {
            this.title = "drink.cold." + title;
            this.ingredientList = list.get(title);
        }

        public static List<String> getAll() {
            return list.keySet().stream().toList();
        }
    }
}