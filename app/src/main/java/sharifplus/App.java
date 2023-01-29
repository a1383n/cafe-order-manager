package sharifplus;

import sharifplus.core.io.Log;
import sharifplus.core.io.Storage;
import sharifplus.feature.auth.model.User;
import sharifplus.feature.auth.view.AuthView;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to SharifPlus .......-.......");

        Log.info("Application started");
        Storage.ensureApplicationFolderInitialized();

        User user = new AuthView().showMainPage();


        System.out.println(user);
    }
}
