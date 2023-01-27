package sharifplus.core.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class View {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public void print(String string) {
        System.out.print(string);
    }

    public void println(String string) {
        System.out.println(string);
    }

    public void printError(String error) {
        System.out.println(ANSI_RED + error + ANSI_RESET);
    }

    public void clear() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            //  Handle any exceptions.
        }
    }

    public void printItems(String[] items) {
        for (int i = 0; i < items.length; i++) {
            println(i + 1 + ". " + items[i]);
        }
    }

    public int printAndWaitForSelectItem(String[] items) {
        printItems(items);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            print("Select one of these: ");

            try {
                int userInput = scanner.nextInt();

                if (userInput > 0 && userInput <= items.length) {
                    return userInput - 1;
                } else {
                    printError("Out of range.");
                }
            }catch (InputMismatchException inputMismatchException) {
                printError("Invalid input");
            }
        }
    }

    public String input(String message) {
        Scanner scanner = new Scanner(System.in);
        print(message);
        return scanner.nextLine();
    }
}
