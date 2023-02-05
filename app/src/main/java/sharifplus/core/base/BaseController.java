package sharifplus.core.base;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import sharifplus.database.Database;

import java.sql.SQLException;
import java.util.List;

/**
 * The base resource controller that's has basic crud methods
 * @param <T> The resource type
 * @param <ID> The id type
 */
public abstract class BaseController<T,ID> {

    /**
     * Database connection source
     */
    protected final ConnectionSource connectionSource = Database.getConnectionSource();

    /**
     * Data access object for operate with database
     */
    protected final Dao<T,ID> dao;

    /**
     * The constructor
     * @param tClass The Class object of resource type. Ex. Order.class
     */
    public BaseController(Class<T> tClass) {
        try {
            // Create the table in database if it's not exists
            TableUtils.createTableIfNotExists(connectionSource,tClass);

            // Create DAO object for specified resource
            this.dao = DaoManager.createDao(connectionSource,tClass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Store the given object to database
     * @param object The object
     */
    protected void create(T object) {
        try {
            dao.create(object);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete the object with specified ID from database
     * @param id The object id
     */
    protected void deleteById(ID id) {
        try {
            dao.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Delete the object from database
     * @param object The object
     */
    protected void delete(T object) {
        try {
            dao.delete(object);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Update the object in database. Note: It's updates all records.
     * @param object The object
     */
    protected void update(T object) {
        try {
            dao.update(object);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get object with specified if from database
     * @param id The object ID
     * @return The object
     */
    protected T getById(ID id) {
        try {
            return dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get all objects exist in database
     * @return The list of objects
     */
    protected List<T> getAll() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
