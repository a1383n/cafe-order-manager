package sharifplus;

import com.j256.ormlite.logger.LocalLogBackend;
import sharifplus.core.io.Log;
import sharifplus.core.io.Storage;
import sharifplus.feature.auth.model.User;
import sharifplus.feature.auth.view.AuthView;

public class App {
    public static void main(String[] args) {

        // Set logging level for ormlite library to ERROR
        System.setProperty(LocalLogBackend.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
        System.out.println("Welcome to SharifPlus .......-.......");

        Log.info("Application started");
        Storage.ensureApplicationFolderInitialized();

        User user = new AuthView().showMainPage();


        System.out.println(user);
    }
}
