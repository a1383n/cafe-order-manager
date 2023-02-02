package sharifplus;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.LocalLogBackend;
import com.j256.ormlite.table.TableUtils;
import sharifplus.core.io.LocalStorage;
import sharifplus.core.io.Log;
import sharifplus.database.Database;
import sharifplus.feature.auth.model.User;
import sharifplus.feature.auth.view.AuthView;
import sharifplus.feature.store.model.Order;
import sharifplus.feature.store.model.products.Food;

import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) throws SQLException {

        // Set logging level for ormlite library to ERROR
        System.setProperty(LocalLogBackend.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
        System.out.println("Welcome to SharifPlus .......-.......");

        Log.info("Application started");
        LocalStorage.ensureApplicationFolderInitialized();

        User user = new AuthView().showMainPage();

        Dao<Order,Integer> dao = DaoManager.createDao(Database.getConnectionSource(),Order.class);
        TableUtils.createTableIfNotExists(Database.getConnectionSource(),Order.class);

        dao.create(new Order(user, List.of(new Food("Pizza"))));

        System.out.println(dao.queryForAll());
    }
}
