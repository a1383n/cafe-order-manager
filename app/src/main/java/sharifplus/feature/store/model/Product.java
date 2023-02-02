package sharifplus.feature.store.model;

import java.util.List;

public abstract class Product {
    public final String title;
    public final List<Ingredient> ingredientList;

    public Product(String title, List<Ingredient> ingredientList) {
        this.title = title;
        this.ingredientList = ingredientList;
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


