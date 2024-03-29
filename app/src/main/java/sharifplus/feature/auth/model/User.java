package sharifplus.feature.auth.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import sharifplus.core.security.Hash;

import java.io.Serializable;

@DatabaseTable(tableName = "users")
public class User implements Serializable {
    /**
     * Should be unique
     */
    @DatabaseField(id = true)
    private String name;
    @DatabaseField
    private Type type;
    @DatabaseField
    private String passwordHash;

    public User(Type type, String name, String password) {
        this.type = type;
        this.name = name;
        this.passwordHash = Hash.make(password);
    }

    /**
     * This empty constructor require for ORMLite library
     */
    public User() {
    }

    public Type getUserType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean checkPasswordHash(String s) {
        return Hash.check(s, this.passwordHash);
    }

    @Override
    public String toString() {
        return "User{" + "type=" + type + ", name='" + name + '\'' + '}';
    }

    public enum Type {
        Customer, Employee
    }
}
