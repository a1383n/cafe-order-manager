package sharifplus.feature.auth.view;

import sharifplus.core.view.View;

public class AuthView extends View {
    public void showMainPage() {
        int selectedIndex = printAndWaitForSelectItem(new String[]{
           "Login",
           "Create account"
        });

        clear();

        switch (selectedIndex){
            case 0:
                println("Login to your account");
                String username = input("Enter the username: ");
                String password = input("Enter the password: ");
                System.out.println("username = " + username);
                System.out.println("password = " + password);
                break;
            case 1:
                println("Create a new account");
                String accountType = input("Enter the account type [Customer,Employee]: ");
                username = input("Enter the username: ");
                password = input("Enter the password: ");
                System.out.println("username = " + username);
                System.out.println("password = " + password);
                break;
        }
    }
}
