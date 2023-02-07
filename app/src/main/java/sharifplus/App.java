package sharifplus;

import com.j256.ormlite.logger.LocalLogBackend;
import com.j256.ormlite.logger.LogBackendType;
import com.j256.ormlite.logger.LoggerFactory;
import sharifplus.core.io.LocalStorage;
import sharifplus.core.io.Log;
import sharifplus.core.view.View;
import sharifplus.feature.auth.model.User;
import sharifplus.feature.auth.view.AuthView;
import sharifplus.feature.store.view.EmployeeView;
import sharifplus.feature.store.view.UserView;

public class App {
    public static void main(String[] args) {

        LoggerFactory.setLogBackendType(LogBackendType.LOCAL);
        System.setProperty(LocalLogBackend.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");

        System.out.println("Welcome to SharifPlus .......-.......");
        Log.info("Application started");
        LocalStorage.ensureApplicationFolderInitialized();

        new MainView().showMain();
    }

    private static class MainView extends View {
        public void showMain() {
            while (true) {
                User user = new AuthView().showMainPage();

                switch (user.getUserType()) {
                    case Employee -> new EmployeeView().showMain();
                    case Customer -> new UserView(user).showMain();
                }

                String s = (String) input("Enter :q to quit the app or press any key to logout: ", "string");

                if (s.equals(":q")) {
                    break;
                } else {
                    println("You're logged out", ANSI_YELLOW);
                }
            }
        }
    }
}
