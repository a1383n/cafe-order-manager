package sharifplus.feature.auth.view;

import sharifplus.core.view.View;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AuthView extends View {
    public void showMainPage() {
        int selectedIndex = printAndWaitForSelectItem(new String[]{
           "Login",
           "Create account"
        });

        Map<String,String> parameterMap = new LinkedHashMap<>();
        Map<String,Object> outputMap = new HashMap<>();

        parameterMap.put("username","String");
        parameterMap.put("password","String");

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

        System.out.print(outputMap);
    }
}
