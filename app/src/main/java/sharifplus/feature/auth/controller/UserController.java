package sharifplus.feature.auth.controller;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.table.TableUtils;
import sharifplus.database.Database;
import sharifplus.feature.auth.model.User;

import java.sql.SQLException;

/**
 * This class control actions for {@link User} class, Such as create or delete
 */
public class UserController {

    /**
     * It's Data access object for CRUD operations
     */
    private final Dao<User, String> userDao;
    public User currentUser;

    public UserController() {
        try {
            userDao = DaoManager.createDao(Database.getConnectionSource(), User.class);
            TableUtils.createTableIfNotExists(Database.getConnectionSource(), User.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Create account with given parameter
     *
     * @param type     The account type
     * @param username The username
     * @param password The password
     * @return If account created, true. If username already exist, false
     */
    public boolean createAccount(User.Type type, String username, String password) {
        User user = new User(type, username, password);

        try {
            if (!userDao.idExists(username)) {
                if (userDao.create(user) == 1) {
                    currentUser = user;
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Login user with given parameter
     *
     * @param username The username
     * @param password The password
     * @return The {@link LoginResult} object will return
     */
    public LoginResult login(String username, String password) {
        try {
            if (!userDao.idExists(username)) return LoginResult.NOT_EXISTS;

            User user = userDao.queryForId(username);

            if (user.checkPasswordHash(password)) {
                currentUser = user;
                return LoginResult.SUCCESSFUL;
            } else {
                return LoginResult.FAILED;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public enum LoginResult {
        SUCCESSFUL, FAILED, NOT_EXISTS
    }
}
