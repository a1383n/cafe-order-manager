package sharifplus.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import sharifplus.core.io.LocalStorage;

import java.nio.file.Path;
import java.sql.SQLException;

/**
 * Database class used to create connection to sqlite database file
 */
public class Database {
    public static final String SQLITE_FILE_NAME = "sqlite.db";
    private static final String SQLITE_JDBC_URL_PREFIX = "jdbc:sqlite:";

    private static ConnectionSource connection;

    public static ConnectionSource getConnectionSource(String databasePath) throws SQLException {
        if (connection != null)
            return connection;

        connection = new JdbcConnectionSource(SQLITE_JDBC_URL_PREFIX + Path.of(databasePath).resolve(SQLITE_FILE_NAME));
        return connection;
    }

    public static ConnectionSource getConnectionSource() {
        if (connection != null)
            return connection;

        try {
            connection = getConnectionSource(LocalStorage.getApplicationDataFolder());
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
