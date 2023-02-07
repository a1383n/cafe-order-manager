package sharifplus.core.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import sharifplus.feature.store.model.Ingredient;
import sharifplus.feature.store.model.Product;

import java.lang.reflect.Type;
import java.util.List;

/**
 * This class used to deserializer the {@link Product} class from JSON. Because the {@code GSON} library not support abstract class deserializer by itself.
 */
public class ProductDeserializer implements JsonDeserializer<Product> {
    @Override
    public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        Type type = new TypeToken<List<Ingredient>>() {
        }.getType();

        return new Product.Builder(object.get("title").getAsString(), context.deserialize(object.get("ingredientList"), type));
    }
}
