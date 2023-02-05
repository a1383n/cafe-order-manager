package sharifplus.feature.store.view;

import sharifplus.core.utils.DateUtil;
import sharifplus.core.view.View;
import sharifplus.feature.store.controller.OrderController;
import sharifplus.feature.store.model.Order;
import sharifplus.feature.store.model.Product;

import java.util.List;
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

    }
}
