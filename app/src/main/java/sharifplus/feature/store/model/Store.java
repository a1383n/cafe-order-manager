package sharifplus.feature.store.model;

import sharifplus.core.utils.Pair;
import sharifplus.feature.auth.model.User;
import sharifplus.feature.store.controller.OrderController;
import sharifplus.feature.store.controller.StorageController;

import java.util.List;

/**
 * The store class
 */
public abstract class Store {
    /**
     * Get menu
     *
     * @return Ex.
     * [
     * "string": [
     * "string": [
     * "string"
     * ]
     * ]
     * ]
     */
    public abstract List<Pair<String, List<Pair<String, List<String>>>>> getMenu();

    /**
     * Add order
     * @param user The user that's order related to
     * @param productList The product list
     * @return If all products integrands are available it's will be true, Otherwise it's false
     */
    public boolean addOrder(User user,List<Product> productList) {
        OrderController orderController = new OrderController();
        StorageController storageController = new StorageController();

        Order order = new Order(user,productList);
        if (storageController.minesOrderIngredients(order)) {
            orderController.store(order);
            return true;
        }else{
            return false;
        }
    }
}
