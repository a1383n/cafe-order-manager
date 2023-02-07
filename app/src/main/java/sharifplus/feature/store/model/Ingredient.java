package sharifplus.feature.store.model;

public record Ingredient(Ingredients ingredient, int value) {

    @Override
    public String toString() {
        return "Ingredient{" + "name=" + ingredient + ", value=" + value + '}';
    }
}