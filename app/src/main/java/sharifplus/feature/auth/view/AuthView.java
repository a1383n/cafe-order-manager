package sharifplus.feature.auth.view;

import sharifplus.core.view.View;
import sharifplus.feature.auth.controller.UserController;
import sharifplus.feature.auth.model.User;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class used to authenticate user
 */
public class AuthView extends View {

    private final UserController controller;

    public AuthView() {
        this.controller = new UserController();
    }

    /**
     * Main page that user select between Login and Create account
     *
     * @return
     */
    public User showMainPage() {
        int selectedIndex = printAndWaitForSelectItem(new String[]{
                "Login",
                "Create account"
        });

        Map<String, String> parameterMap = new LinkedHashMap<>();
        Map<String, Object> outputMap = new HashMap<>();

        parameterMap.put("username", "String");
        parameterMap.put("password", "String");

        // Infinity loop until user can login or create account
        while (true) {
            switch (selectedIndex) {
                case 0 -> {
                    println("Login to your account");
                    outputMap = readParameters(parameterMap);
                }
                case 1 -> {
                    println("Create a new account");
                    parameterMap.put("account type", "string");
                    outputMap = readParameters(parameterMap);
                }
            }

            if (parameterMap.containsKey("account type")) {
                boolean isCreated = controller.createAccount(User.Type.valueOf(outputMap.get("account type").toString()), outputMap.get("username").toString(), outputMap.get("password").toString());

                if (isCreated) {
                    println("Account created.", ANSI_GREEN);
                    return controller.currentUser;
                } else {
                    println("This username already exists", ANSI_RED);
                }
            } else {
                UserController.LoginResult result = controller.login(parameterMap.get("username"), parameterMap.get("password"));

                switch (result) {
                    case SUCCESSFUL -> {
                        println("Login successful");
                        return controller.currentUser;
                    }
                    case FAILED -> println("Username or password is incorrect", ANSI_RED);
                    case NOT_EXISTS -> println("This account is not exists", ANSI_RED);
                }
            }
        }
    }
}
