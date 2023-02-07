package sharifplus.feature.store.model;

import sharifplus.feature.store.model.products.Appetizer;
import sharifplus.feature.store.model.products.Dessert;
import sharifplus.feature.store.model.products.Drink;
import sharifplus.feature.store.model.products.Food;

import java.util.List;

public abstract class Product {
    public final String title;
    public final List<Ingredient> ingredientList;

    public Product(String title, List<Ingredient> ingredientList) {
        this.title = title;
        this.ingredientList = ingredientList;
    }

    /**
     * Return product from string. Ex. food.pizza -> new Food("Pizza")
     *
     * @param s The String
     * @return The Product
     */
    public static Product valueOf(String s) {
        String[] strings = s.split("\\.");

        return switch (strings[0]) {
            case "food" -> new Food(strings[1]);
            case "appetizer" -> new Appetizer(strings[1]);
            case "dessert" -> new Dessert(strings[1]);
            case "drink" -> switch (strings[1]) {
                case "hot" -> new Drink.Hot(strings[2]);
                case "cold" -> new Drink.Cold(strings[2]);
                default -> throw new RuntimeException();
            };
            default -> throw new RuntimeException();
        };
    }

    /**
     * Export product names to show in tables
     *
     * @param productList The product list
     * @return Ex. Pizza, Soda
     */
    public static String exportAllProductsNames(List<Product> productList) {
        List<String> strings = productList.stream().map(product -> {
            String[] array = product.title.split("\\.");
            return array[array.length - 1];
        }).toList();
        return String.join(",", strings);
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", ingredientList=" + ingredientList +
                '}';
    }

    /**
     * We can't create new instance for abstract class, so the seperated class that's extend from that can do this
     */
    public static class Builder extends Product {
        public Builder(String title, List<Ingredient> ingredientList) {
            super(title, ingredientList);
        }
    }
}


