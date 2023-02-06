package sharifplus.feature.store.controller;

import sharifplus.core.io.LocalStorage;
import sharifplus.feature.store.model.Ingredient;
import sharifplus.feature.store.model.Order;
import sharifplus.feature.store.model.Product;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class StorageController {
    /**
     * The min value of random number to fill ingredient value
     */
    private static final int RANDOM_MIN_VALUE = 5;

    /**
     * The max value of random number to fill ingredient value
     */
    private static final int RANDOM_MAX_VALUE = 20;

    /**
     * Storage file name that's saved in {@link LocalStorage#getApplicationDataFolder()}
     */
    public static final String STORAGE_FILE_NAME = "storage.properties";

    /**
     * The prefix of configuration keys
     */
    public static final String STORAGE_KEY_PREFIX = "storage.ingredient.";

    /**
     * Path of configuration file
     */
    private static final Path storageFilePath = Path.of(LocalStorage.getApplicationDataFolder()).resolve(STORAGE_FILE_NAME);

    /**
     * The main variable that's keep ingredients in program
     */
    private final Map<Ingredient.Ingredients, Integer> ingredientsMap;

    public StorageController() {
        ingredientsMap = loadValuesFromFile();
    }

    /**
     * Load ingredients values from file. If file not exist. That's filled with random values
     * @return The map of ingredients and values
     */
    private Map<Ingredient.Ingredients, Integer> loadValuesFromFile() {
        Ingredient.Ingredients[] ingredients = Ingredient.Ingredients.values();
        Map<Ingredient.Ingredients, Integer> map = new HashMap<>();
        Properties properties = new Properties();
        Random random = new Random();

        try {
            properties.load(new FileInputStream(storageFilePath.toFile()));
            for (Ingredient.Ingredients ingredient : ingredients) {
                map.put(ingredient, Integer.parseInt(String.valueOf(properties.getOrDefault(STORAGE_KEY_PREFIX + ingredient.name().toLowerCase(), random.nextInt(5, 20)))));
            }
            return map;
        } catch (IOException e) {
            for (Ingredient.Ingredients ingredient : ingredients) {
                map.put(ingredient, random.nextInt(RANDOM_MIN_VALUE, RANDOM_MAX_VALUE));
            }
            return map;
        }
    }

    /**
     * Save the current {@link #ingredientsMap} to the specified file {@link #storageFilePath}
     */
    private void save() {
        Properties properties = new Properties();
        ingredientsMap.forEach((ingredients, integer) -> {
            properties.put(STORAGE_KEY_PREFIX + ingredients.name().toLowerCase(), integer.toString());
        });

        try {
            properties.store(new FileOutputStream(storageFilePath.toFile()), null);
        } catch (FileNotFoundException e) {
            try {
                storageFilePath.toFile().createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add x value to specified ingredient
     * @param ingredient The ingredient
     * @param value The x value
     */
    public void addValue(Ingredient.Ingredients ingredient, int value) {
        setValue(ingredient, ingredientsMap.get(ingredient) + value);
    }

    /**
     * Mines x value to specified ingredient
     * @param ingredient The ingredient
     * @param value The x value
     */
    public void minesValue(Ingredient.Ingredients ingredient, int value) {
        setValue(ingredient, ingredientsMap.get(ingredient) - value);
    }

    /**
     * Set x value to specified ingredient
     * @param ingredient The ingredient
     * @param value The x value
     */
    public void setValue(Ingredient.Ingredients ingredient, int value) {
        ingredientsMap.put(ingredient, value);
        save();
    }

    /**
     * Add list of the values to list of ingredients. Ex. [Meat,Bread],[3,5] --> Meat + 3, Bread + 5
     * @param ingredients The ingredients
     * @param values The values
     */
    public void addValues(List<Ingredient.Ingredients> ingredients, List<Integer> values) {
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientsMap.put(ingredients.get(i), ingredientsMap.get(ingredients.get(i)) + values.get(i));
        }
        save();
    }

    /**
     * Check is had enough values for specified {@link Ingredient}
     * @param ingredient The ingredient
     * @return If storage value gather or equal to specified value, will true. Otherwise false
     */
    public boolean checkIngredient(Ingredient ingredient) {
        return ingredientsMap.get(ingredient.ingredient) >= ingredient.value;
    }

    /**
     * Check is had enough values for specified {@link Ingredient}. It's check all ingredients of all products
     * @param order The order
     * @return If all ingredients enough will true. Otherwise false
     */
    public boolean checkOrder(Order order) {
        for (int i = 0; i < order.productList.size(); i++) {
            Product product = order.productList.get(i);
            for (int j = 0; j < product.ingredientList.size(); j++) {
                if (!checkIngredient(product.ingredientList.get(j)))
                    return false;
            }
        }

        return true;
    }

    /**
     * Mines order ingredients from storage
     * @param order The order
     */
    public void minesOrderIngredients(Order order) {
        for (int i = 0; i < order.productList.size(); i++) {
            Product product = order.productList.get(i);
            for (int j = 0; j < product.ingredientList.size(); j++) {
                Ingredient ingredient = product.ingredientList.get(j);
                minesValue(ingredient.ingredient, ingredient.value);
            }
        }
    }

    /**
     * Get current ingredients values
     * @return The ingredients values map
     */
    public Map<Ingredient.Ingredients, Integer> getIngredientsValues() {
        return ingredientsMap;
    }
}
