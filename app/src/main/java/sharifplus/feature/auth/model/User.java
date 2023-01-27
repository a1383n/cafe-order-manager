package sharifplus.feature.auth.model;

import sharifplus.core.security.Hash;

enum UserType {
    Customer,
    Employee
}

public class User {
    public User(UserType userType, String name, String password) {
        this.userType = userType;
        this.name = name;
        this.passwordHash = Hash.make(password);
    }

    private final UserType userType;

    /**
     * Should be unique
     */
    private final String name;

    private final String passwordHash;

    public UserType getUserType() {
        return userType;
    }

    public String getName() {
        return name;
    }

    public boolean checkPasswordHash(String s) {
        return Hash.check(s,this.passwordHash);
    }
}
