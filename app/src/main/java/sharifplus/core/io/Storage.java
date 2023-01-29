package sharifplus.core.io;

import java.io.File;
import java.nio.file.Path;

public class Storage {
    public static final String APPLICATION_DATA_FOLDER_NAME = ".sharif_plus";

    /**
     * Get the current folder that's application running. Ex. ~/IdeaProjects/SharifPlus
     *
     * @return The path of folder
     */
    public static String getCurrentDirectory() {
        return System.getProperty("user.dir");
    }

    /**
     * Get the application data folder that's specified in {@link #APPLICATION_DATA_FOLDER_NAME} from absolutePath
     *
     * @param absolutePath The absolute path that's folder should create
     * @return The path of folder
     */
    public static String getApplicationDataFolder(String absolutePath) {
        return Path.of(absolutePath).resolve(APPLICATION_DATA_FOLDER_NAME).toString();
    }

    /**
     * Get the application data folder in current directory
     *
     * @return The path of folder
     */
    public static String getApplicationDataFolder() {
        return getApplicationDataFolder(getCurrentDirectory());
    }


    public static void ensureApplicationFolderInitialized() {
        File applicationFolder = new File(getApplicationDataFolder());

        if (!applicationFolder.exists()) {
            applicationFolder.mkdir();
        }
    }
}
