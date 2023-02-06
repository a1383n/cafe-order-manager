package sharifplus.feature.store.view;

import de.vandermeer.asciitable.AsciiTable;
import sharifplus.core.utils.DateUtil;
import sharifplus.core.utils.StringUtils;
import sharifplus.core.view.View;
import sharifplus.feature.store.controller.OrderController;
import sharifplus.feature.store.controller.StorageController;
import sharifplus.feature.store.model.Ingredient;
import sharifplus.feature.store.model.Order;
import sharifplus.feature.store.model.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * When logged in user is Employee, This view should use
 */
public class EmployeeView extends View {
    private static final String[] orderItems = new String[]{
            "Queued orders",
            "All orders"
    };

    private static final String[] storageItems = new String[]{
            "View Inventory",
            "Manage Inventory"
    };

    public void showMain() {
        while (true) {
            int selectedItem = printAndWaitForSelectItem(new String[]{
                    "Orders",
                    "Inventory"
            }, true);

            if (selectedItem == -1)
                break;

            switch (selectedItem) {
                case 0 -> showOrderItems();
                case 1 -> showInventoryItems();
            }
        }
    }

    private void showOrderItems() {
        OrderController controller = new OrderController();

        // Need high skills to understand what's happening here
        while (true) {
            int selectedItem = printAndWaitForSelectItem(orderItems, true);

            if (selectedItem == -1)
                break;

            switch (selectedItem) {
                case 0 -> {
                    while (true) {
                        List<Order> orders = controller.getInQueueOrdersSortedByDate();
                        printASCIITable(orders.stream().map(order -> new String[]{
                                String.valueOf(order.id),
                                order.user.getName(),
                                Product.exportAllProductsNames(order.productList),
                                DateUtil.diffWithNow(order.createdAt),
                        }).toList(), "ID", "User", "Products", "Created At");

                        int index = printAndWaitForSelectItem(new String[]{
                                "Mark as delivered",
                                "Cancel the order"
                        }, true);

                        if (index == -1)
                            break;

                        switch (index) {
                            case 0 -> {
                                int id = (int) input("Enter the id: ", "int");
                                Optional<Order> order = orders.stream().filter(o -> o.id == id).findFirst();

                                if (order.isPresent()) {
                                    controller.markAsDelivered(order.get());
                                    println("Order marks as delivered", ANSI_GREEN);
                                } else {
                                    println("This id is already delivered or canceled", ANSI_RED);
                                }
                            }
                            case 1 -> {
                                int id = (int) input("Enter the id: ", "int");
                                Optional<Order> order = orders.stream().filter(o -> o.id == id).findFirst();

                                if (order.isPresent()) {
                                    controller.cancelOrder(order.get().id);
                                    println("Order canceled", ANSI_GREEN);
                                } else {
                                    println("This id is delivered or not exits", ANSI_RED);
                                }
                            }
                        }
                    }
                }
                case 1 ->
                        printASCIITable(controller.getAllOrdersSortedByDeliveredDate().stream().map(order -> new String[]{
                                String.valueOf(order.id),
                                order.user.getName(),
                                Product.exportAllProductsNames(order.productList),
                                DateUtil.diffWithNow(order.createdAt),
                                DateUtil.diffWithNow(order.deliveredAt)
                        }).toList(), "ID", "User", "Products", "Created At", "Delivered At");
            }
        }
    }

    private void showInventoryItems() {
        StorageController controller = new StorageController();

        while (true) {
            int selectedIndex = printAndWaitForSelectItem(storageItems, true);

            if (selectedIndex == -1)
                break;

            switch (selectedIndex) {
                case 0 -> {
                    Map<Ingredient.Ingredients, Integer> map = controller.getIngredientsValues();

                    AsciiTable table = new AsciiTable();
                    table.addRule();
                    table.addRow("Ingredient", "Value");
                    table.addRule();

                    map.forEach((ingredients, integer) -> {
                        table.addRow(StringUtils.toTitleCase(ingredients.name().toLowerCase()).replace('_', ' '), integer);
                        table.addRule();
                    });

                    println(table.render(35));
                }
                case 1 -> {
                    Map<Ingredient.Ingredients, Integer> map = controller.getIngredientsValues();
                    int index = printAndWaitForSelectItem(map.keySet().stream().map(v -> StringUtils.toTitleCase(v.name().toLowerCase()).replace('_', ' ')).toList().toArray(new String[]{}), true);

                    if (index == -1)
                        break;

                    String value = (String) input("Enter the value (+2,-2,2): ", "string");

                    if (value.startsWith("+")) {
                        controller.addValue(map.keySet().stream().toList().get(index), Integer.parseInt(value.replace("+", "")));
                    } else if (value.startsWith("-")) {
                        int intValue = Integer.parseInt(value.replace("-", ""));
                        if (intValue > map.values().stream().toList().get(index)) {
                            println("Out of range.", ANSI_RED);
                            continue;
                        } else {
                            controller.minesValue(map.keySet().stream().toList().get(index), intValue);
                        }
                    } else {
                        controller.setValue(map.keySet().stream().toList().get(index), Integer.parseInt(value));
                    }

                    println("The inventory value changed successfully", ANSI_GREEN);
                }
            }
        }
    }
}
