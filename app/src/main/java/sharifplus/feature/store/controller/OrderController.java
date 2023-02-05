package sharifplus.feature.store.controller;

import sharifplus.core.base.BaseController;
import sharifplus.feature.store.model.Order;

import java.sql.SQLException;
import java.util.List;

public class OrderController extends BaseController<Order,Integer> {

    public OrderController() {
        super(Order.class);
    }

    /**
     * Get all in queue record, order by {@link Order#createdAt} descending
     * @return The list of records
     */
    public List<Order> getInQueueOrdersSortedByDate() {
        try {
            return dao.queryBuilder()
                    .where()
                    .isNull("deliveredAt")
                    .queryBuilder()
                    .orderBy("createdAt",false)
                    .query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all record, order by {@link Order#deliveredAt} descending
     * @return
     */
    public List<Order> getAllOrdersSortedByDeliveredDate() {
        try {
            return dao.queryBuilder()
                    .orderBy("deliveredAt",false)
                    .query();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cancel the pending order by deleting that from database
     * @param id The order id
     * @return If order cancel successfully it's will be true, otherwise order is not pending or not exists and will be false
     */
    public boolean cancelOrder(int id) {
        Order order = getById(id);

        if (order == null || order.isDelivered())
            return false;

        deleteById(id);

        return true;
    }

    /**
     * Mark Order as delivered
     * @param order
     */
    public void markAsDelivered(Order order) {
        order.setDeliveredAt();
        update(order);
    }
}
