package sharifplus.core.utils;

import sharifplus.core.io.LocalStorage;

import java.io.*;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

/**
 * This class use to load and store application config data based on {@link Properties} class
 */
public class Environment {
    private static final String CONFIG_FILE_NAME = "config.properties";

    private static final Path localFilePath = Path.of(LocalStorage.getApplicationDataFolder()).resolve(CONFIG_FILE_NAME);

    public final Properties properties;

    /**
     * Load environment from {@link InputStream}. The inputStream pass to {@link Properties} class
     *
     * @param inputStream The input stream
     */
    public Environment(InputStream inputStream) {
        this.properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Try to load configuration. First check if configuration was exists in application data folder then load that from otherwise load configuration from resource that's bundle with .jar file and now in resource folder
     *
     * @throws IOException if an I/O exception occurs.
     */
    public static Environment load() throws IOException {
        if (new File(localFilePath.toUri()).exists()) {
            return new Environment(new FileInputStream(new File(localFilePath.toUri())));
        } else {
            return new Environment(Objects.requireNonNull(Environment.class.getClassLoader().getResource(CONFIG_FILE_NAME)).openStream());
        }
    }

    /**
     * Save current {@link #properties} object to application data folder
     *
     * @throws IOException if an I/O exception occurs.
     */
    public void save() throws IOException {
        if (new File(localFilePath.toUri()).exists()) {
            try {
                properties.store(new FileOutputStream(new File(localFilePath.toUri())), null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            new File(localFilePath.toUri()).createNewFile();
            save();
        }
    }
}
