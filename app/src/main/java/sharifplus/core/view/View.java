package sharifplus.core.view;

import de.vandermeer.asciitable.AsciiTable;

import java.util.List;
import java.util.Scanner;

public abstract class View extends Console {

    /**
     * Print items using {@link #printItems(String[])} method the waiting for user to select one of these.
     *
     * @param items         The array of items should print and user select between that's
     * @param hasBackOption If it's true then menu had back option
     * @return The index of selected item. If user select back option, then -1 will be returned.
     */
    public int printAndWaitForSelectItem(String[] items, boolean hasBackOption) {
        printItems(items);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            print("Select one of these" + (hasBackOption ? " (:b for navigate back): " : ": "));

            try {
                String userInput = scanner.nextLine();

                if (hasBackOption && userInput.equals(":b")) return -1;

                int selectedIndex = (int) cast(userInput, "int");

                if (selectedIndex > 0 && selectedIndex <= items.length) {
                    return selectedIndex - 1;
                } else {
                    println("Out of range.", ANSI_RED);
                }
            } catch (Exception exception) {
                println("Invalid input", ANSI_RED);
            }
        }
    }

    /**
     * Print the ascii table
     *
     * @param rows   The table rows
     * @param header The table header
     */
    public void printASCIITable(List<String[]> rows, String... header) {
        AsciiTable table = new AsciiTable();

        table.addRule();
        table.addRow((Object[]) header);
        table.addRule();

        for (String[] row : rows) {
            table.addRow((Object[]) row);
            table.addRule();
        }

        println(table.render());
    }

    /**
     * Read user input from stdin with {@link Scanner}
     *
     * @param message The message for explain what you need from user
     * @return The user given value
     */
    public Object input(String message, String type) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            print(message);

            try {
                String userInput = scanner.nextLine();
                return cast(userInput, type);
            } catch (Exception e) {
                println("Invalid Input", ANSI_RED);
            }
        }
    }
}
