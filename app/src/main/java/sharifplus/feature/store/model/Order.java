package sharifplus.feature.store.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import sharifplus.core.utils.ProductDeserializer;
import sharifplus.feature.auth.model.User;

import java.util.Date;
import java.util.List;

/**
 * Order class to hold the person who registers it and the requested products
 */
@DatabaseTable(tableName = "orders")
public class Order {

    /**
     * The unique id for order
     */
    @DatabaseField(generatedId = true)
    public int id;

    /**
     * Note: When {@link Order} retrieved from the database, User object only had {@link User#getName()} and other fields will be {@code null}. Because the {@code Ormlite} library not support that.
     */
    @DatabaseField(canBeNull = false, foreign = true)
    public User user;
    /**
     * The {@link Product} list of user
     */
    public List<Product> productList;
    /**
     * The timestamp that's tell when the order created
     * Its store in database as UNIX-Time
     */
    @DatabaseField(canBeNull = false, dataType = DataType.DATE)
    public Date createdAt;
    /**
     * The timestamp that's tell when the order was delivered
     * Its store in database as UNIX-Time
     * It can be null. If it's null means order not deviled.
     */
    @DatabaseField(dataType = DataType.DATE)
    public Date deliveredAt;
    /**
     * We convert {@link #productList} to json for keep that in database
     */
    @DatabaseField(canBeNull = false, useGetSet = true)
    private String productListJson;

    /**
     * Create Order object
     *
     * @param user        The user that's order related to.
     * @param productList The products list that's user want
     */
    public Order(User user, List<Product> productList) {
        this.user = user;
        this.productList = productList;
        this.createdAt = new Date();
    }

    /**
     * This empty constructor for {@code Ormlite} library
     */
    Order() {
    }

    /**
     * The getter {@link #productListJson} that's return the json to store in the database
     *
     * @return The json string
     */
    public String getProductListJson() {
        return new Gson().toJson(productList);
    }

    /**
     * The setter for {@link #productListJson} that's call when object loaded from database. It's get json string and parse it and fill the {@link #productList}
     */
    public void setProductListJson(String productListJson) {
        this.productListJson = productListJson;
        this.productList = new GsonBuilder()
                .registerTypeAdapter(Product.class, new ProductDeserializer())
                .create()
                .fromJson(this.productListJson, new TypeToken<List<Product>>() {
                }.getType());
    }

    /**
     * Fill the {@link #deliveredAt} with {@link Date#Date()} that's current time
     */
    public void setDeliveredAt() {
        this.deliveredAt = new Date();
    }

    /**
     * Check if order is delivered or not
     *
     * @return If {@link #deliveredAt} equal to {@code null} means order not delivered
     */
    public boolean isDelivered() {
        return deliveredAt != null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", user=" + user +
                ", productList=" + productList +
                ", createdAt=" + createdAt +
                ", deliveredAt=" + deliveredAt +
                '}';
    }
}
