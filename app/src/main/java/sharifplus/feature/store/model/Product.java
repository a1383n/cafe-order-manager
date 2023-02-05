package sharifplus.feature.store.model;

import java.util.List;

public abstract class Product {
    public final String title;
    public final List<Ingredient> ingredientList;

    public Product(String title, List<Ingredient> ingredientList) {
        this.title = title;
        this.ingredientList = ingredientList;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", ingredientList=" + ingredientList +
                '}';
    }

    /**
     * Export product names to show in tables
     * @param productList The product list
     * @return Ex. Pizza, Soda
     */
    public static String exportAllProductsNames(List<Product> productList) {
        List<String> strings = productList.stream().map(product -> product.title.split("\\.")[1]).toList();
        return String.join(",", strings);
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


