package sharifplus.core.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * No idea about this class
 */
public class FileController implements Closeable {
    public Path path;
    private File file;
    private Scanner reader;

    private FileWriter writer;

    /**
     * Create controller with specified file path
     *
     * @param filePath     The file path. Ex. log.txt or /logs/app.log
     * @param absolutePath The absolute path that's combine with filepath. Ex. ~/.data
     * @throws IOException If system cannot create file in the specified patch, If systemc cannot create parents directories, If path point to directory
     */
    public FileController(String filePath, String absolutePath) throws IOException {
        path = absolutePath != null ? Path.of(absolutePath).resolve(filePath) : Path.of(filePath);
        file = new File(path.toUri());

        if (file.isDirectory())
            throw new IOException("The path point to the directory");

        initialize();
    }

    /**
     * Create controller that's absolutePath is set to {@link Storage#getApplicationDataFolder()} method result
     *
     * @param filePath The file path. Ex. log.txt or /logs/app.log
     * @return The controller
     * @throws IOException If system cannot create file in the specified patch, If systemc cannot create parents directories, If path point to directory
     */
    public static FileController inAppData(String filePath) throws IOException {
        String absolutePath = Storage.getApplicationDataFolder();
        File absoluteFile = new File(absolutePath);

        if (absoluteFile.exists()) {
            return new FileController(filePath, absolutePath);
        } else {
            if (!absoluteFile.mkdir()) {
                throw new IOException("The system cannot create parent directory");
            } else {
                return new FileController(filePath, absolutePath);
            }
        }
    }

    /**
     * Initialize file. If it's not exist then create that also if parent directory not exist create it too.
     *
     * @throws IOException If any IO issue in create file or directory happened
     */
    private void initialize() throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    /**
     * Write the data to a file
     *
     * @param s The string data
     * @throws IOException if an I/O exception occurs.
     */
    public void writeLine(String s) throws IOException {
        if (writer == null)
            writer = new FileWriter(file);

        writer.write(s);
        writer.flush();
    }

    /**
     * Read the data from a file
     *
     * @return The data
     * @throws IOException if an I/O exception occurs.
     */
    public Stream<String> readLines() throws IOException {
        return Files.lines(path);
    }

    /**
     * Append the new data to a file
     *
     * @param s The data
     * @throws IOException if an I/O exception occurs.
     */
    public void appendLine(String s) throws IOException {
        if (writer == null)
            writer = new FileWriter(file);

        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        PrintWriter printWriter = new PrintWriter(bufferedWriter);

        printWriter.println(s);
    }

    /**
     * Close all resource
     *
     * @throws IOException if an I/O exception occurs.
     */
    @Override
    public void close() throws IOException {
        if (writer != null)
            writer.close();

        if (reader != null)
            reader.close();
    }
}
