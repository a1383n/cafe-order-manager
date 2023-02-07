package sharifplus.feature.store.controller;

import sharifplus.core.base.BaseController;
import sharifplus.feature.auth.model.User;
import sharifplus.feature.store.model.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderController extends BaseController<Order, Integer> {

    public OrderController() {
        super(Order.class);
    }

    /**
     * Get all in queue record, order by {@link Order#createdAt} descending
     *
     * @return The list of records
     */
    public List<Order> getInQueueOrdersSortedByDate() {
        try {
            return dao.queryBuilder().where().isNull("deliveredAt").queryBuilder().orderBy("createdAt", false).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all record, order by {@link Order#deliveredAt} descending
     *
     * @return The orders list
     */
    public List<Order> getAllOrdersSortedByDeliveredDate() {
        try {
            return dao.queryBuilder().orderBy("deliveredAt", false).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all record that's related to specified user.
     *
     * @param user The user
     * @return The list of orders
     */
    public List<Order> getAllUserOrdersSortedByCreatedDate(User user) {
        try {
            return dao.queryBuilder().where().eq("user_id", user.getName()).queryBuilder().orderBy("createdAt", false).query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cancel the pending order by deleting that from database
     *
     * @param id The order id
     */
    public void cancelOrder(int id) {
        Order order = getById(id);

        if (order == null || order.isDelivered()) return;

        deleteById(id);
    }

    /**
     * Mark Order as delivered
     *
     * @param order The order
     */
    public void markAsDelivered(Order order) {
        order.setDeliveredAt();
        update(order);
    }

    /**
     * Store the order into database
     *
     * @param order The Order
     */
    public void store(Order order) {
        create(order);
    }
}
