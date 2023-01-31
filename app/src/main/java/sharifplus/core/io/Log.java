package sharifplus.core.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

enum LogLevel {
    INFO,
    ERROR
}

/**
 * This class use to write log file
 */
public class Log {

    private static final String LOG_FILE_NAME = "log.txt";

    /**
     * Write log with info {@link LogLevel}
     *
     * @param s The log message
     */
    public static void info(String s) {
        log(s, LogLevel.INFO);
    }

    /**
     * Log the given message with given {@link LogLevel}
     *
     * @param s     The log message
     * @param level The log level
     */
    public static void log(String s, LogLevel level) {
        try {
            Path path = Path.of(LocalStorage.getApplicationDataFolder()).resolve(LOG_FILE_NAME);
            File file = new File(path.toUri());

            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            /*
              Output:
              [2023-01-28T21:52:28.7173746] - (INFO): TEST
             */
            Files.writeString(
                    Path.of(LocalStorage.getApplicationDataFolder()).resolve("log.txt"),
                    String.format("[%s] - (%s): %s", ZonedDateTime.now(ZoneId.of("Asia/Tehran")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), level.name(), s) + System.lineSeparator(),
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
