package sharifplus.feature.store.view;

import sharifplus.core.utils.DateUtil;
import sharifplus.core.utils.Pair;
import sharifplus.core.view.View;
import sharifplus.feature.auth.model.User;
import sharifplus.feature.store.controller.OrderController;
import sharifplus.feature.store.model.Product;
import sharifplus.feature.store.model.store.Cafe;
import sharifplus.feature.store.model.store.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserView extends View {
    private final User user;

    public UserView(User user) {
        this.user = user;
    }

    public void showMain() {
        while (true) {
            int selectedIndex = printAndWaitForSelectItem(new String[]{
                    "Restaurant",
                    "Cafe",
                    "Order history"
            }, true);

            if (selectedIndex == -1)
                return;

            switch (selectedIndex) {
                case 0 -> showRestaurant();
                case 1 -> showCafe();
                case 2 -> {
                    OrderController controller = new OrderController();
                    printASCIITable(controller.getAllUserOrdersSortedByCreatedDate(user).stream().map(order -> new String[]{
                            String.valueOf(order.id),
                            order.user.getName(),
                            Product.exportAllProductsNames(order.productList),
                            DateUtil.diffWithNow(order.createdAt),
                            DateUtil.diffWithNow(order.deliveredAt)
                    }).toList(), "ID", "User", "Products", "Created At", "Delivered At");
                }
            }
        }
    }

    private List<Product> printMenuAndWaitForUserSelectProducts(List<Pair<String, List<Pair<String, List<String>>>>> menu) {
        List<String> list = new ArrayList<>();
        for (Pair<String, List<Pair<String, List<String>>>> stringListPair : menu) {
            println(stringListPair.key() + ":");
            for (int j = 0; j < stringListPair.value().size(); j++) {
                println("      " + stringListPair.value().get(j).key() + ":");
                for (int k = 0; k < stringListPair.value().get(j).value().size(); k++) {
                    list.add(stringListPair.value().get(j).value().get(k));
                    String[] strings = stringListPair.value().get(j).value().get(k).split("\\.");
                    println("            " + list.size() + ". " + strings[strings.length - 1]);
                }
            }
        }

        List<Product> productList;
        String s = (String) input("Enter the product numbers (Ex. 1 or 1,2,3) (:b for navigate back): ", "string");

        if (s.equals(":b"))
            return null;

        productList = new ArrayList<>();

        try {
            Integer[] numbers;

            if (s.contains(",")) {
                numbers = Arrays.stream(s.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            } else {
                numbers = new Integer[]{Integer.parseInt(s)};
            }

            for (Integer number : numbers) {
                productList.add(Product.valueOf(list.get(number - 1)));
            }
        } catch (NumberFormatException e) {
            println(e.getMessage(), ANSI_RED);
            return null;
        }

        return productList;
    }

    private void showRestaurant() {
        Restaurant restaurant = new Restaurant();

        List<Product> productList = printMenuAndWaitForUserSelectProducts(restaurant.getMenu());
        if (productList == null)
            return;

        if (restaurant.addOrder(user, productList)) {
            println("The order has been created.", ANSI_GREEN);
        } else {
            println("The order cannot created. Because one or more products didn't have enough integrates", ANSI_RED);
        }
    }

    private void showCafe() {
        Cafe cafe = new Cafe();
        List<Product> productList = printMenuAndWaitForUserSelectProducts(cafe.getMenu());

        if (productList == null)
            return;

        if (cafe.addOrder(user, productList)) {
            println("The order has been created.", ANSI_GREEN);
        } else {
            println("The order cannot created. Because one or more products didn't have enough integrates", ANSI_RED);
        }
    }
}
