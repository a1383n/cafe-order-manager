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
    public static final String STORAGE_FILE_NAME = "storage.properties";
    private static final Path storageFilePath = Path.of(LocalStorage.getApplicationDataFolder()).resolve(STORAGE_FILE_NAME);

    private final Map<Ingredient.Ingredients, Integer> ingredientsMap;

    public StorageController() {
        ingredientsMap = loadValuesFromFile();
    }

    private Map<Ingredient.Ingredients, Integer> loadValuesFromFile() {
        Ingredient.Ingredients[] ingredients = Ingredient.Ingredients.values();
        Map<Ingredient.Ingredients, Integer> map = new HashMap<>();
        Properties properties = new Properties();
        Random random = new Random();

        try {
            properties.load(new FileInputStream(storageFilePath.toFile()));
            for (Ingredient.Ingredients ingredient : ingredients) {
                map.put(ingredient, (Integer) properties.getOrDefault("storage.ingredient." + ingredient.name().toLowerCase(), random.nextInt(5, 20)));
            }
            return map;
        } catch (IOException e) {
            for (Ingredient.Ingredients ingredient : ingredients) {
                map.put(ingredient, random.nextInt(5, 20));
            }
            return map;
        }
    }

    private void save() {
        Properties properties = new Properties();
        ingredientsMap.forEach((ingredients, integer) -> {
            properties.put("storage.ingredient." + ingredients.name().toLowerCase(), integer.toString());
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

    public void addValue(Ingredient.Ingredients ingredient, int value) {
        setValue(ingredient, ingredientsMap.get(ingredient) + value);
    }

    public void minesValue(Ingredient.Ingredients ingredient, int value) {
        setValue(ingredient, ingredientsMap.get(ingredient) - value);
    }

    public void setValue(Ingredient.Ingredients ingredient, int value) {
        ingredientsMap.put(ingredient, value);
        save();
    }

    public void addValues(List<Ingredient.Ingredients> ingredients, List<Integer> integers) {
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientsMap.put(ingredients.get(i), ingredientsMap.get(ingredients.get(i)) + integers.get(i));
        }
        save();
    }

    public boolean checkIngredient(Ingredient ingredient) {
        return ingredientsMap.get(ingredient.ingredient) >= ingredient.value;
    }

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

    public void minesOrderIngredients(Order order) {
        for (int i = 0; i < order.productList.size(); i++) {
            Product product = order.productList.get(i);
            for (int j = 0; j < product.ingredientList.size(); j++) {
                Ingredient ingredient = product.ingredientList.get(j);
                minesValue(ingredient.ingredient, ingredient.value);
            }
        }
    }

    public Map<Ingredient.Ingredients, Integer> getIngredientsValues() {
        return ingredientsMap;
    }
}
