package sharifplus.feature.store.model;

public class Ingredient {
    public enum Ingredients {
        FLOUR,
        CHEESE,
        MEAT,
        BREAD,
        VEGETABLE,
        CHICKEN,
        POTATO,
        COFFEE,
        BEANS,
        TEA,
        CHOCOLATE,
        SODA,
        ICE_CREAM,
        EGG,
        VANILLA,
        WATER,
        SUGAR
    }

    public final Ingredients ingredient;
    public final int value;

    public Ingredient(Ingredients ingredient, int value) {
        this.ingredient = ingredient;
        this.value = value;
    }
}