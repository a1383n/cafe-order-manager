package sharifplus.core.view;

import java.util.Scanner;

public abstract class View extends Console {

    /**
     * Print items using {@link #printItems(String[])} method the waiting for user to select one of these.
     * @param items The array of items should print and user select between thats
     * @return The index of selected item
     */
    public int printAndWaitForSelectItem(String[] items) {
        printItems(items);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            print("Select one of these: ");

            try {
                int userInput = (int) cast(scanner.nextLine(),"int");

                if (userInput > 0 && userInput <= items.length) {
                    return userInput - 1;
                } else {
                    println("Out of range.", ANSI_RED);
                }
            } catch (Exception exception) {
                println("Invalid input", ANSI_RED);
            }
        }
    }

    /**
     * Read user input from stdin with {@link Scanner}
     * @param message The message for explain what you need from user
     * @return The user given value
     */
    public String input(String message) {
        Scanner scanner = new Scanner(System.in);
        print(message);
        return scanner.nextLine();
    }
}
