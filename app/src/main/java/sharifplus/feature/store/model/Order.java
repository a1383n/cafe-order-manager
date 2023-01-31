package sharifplus.feature.store.model;

import sharifplus.feature.auth.model.User;

import java.util.List;

public class Order {
    public int id;
    public User user;
    public List<Product> productList;
}
