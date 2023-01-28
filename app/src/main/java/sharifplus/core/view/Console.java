package sharifplus.core.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * This class used to handle console operation such as a print message and read user input
 */
public class Console {
    // ANSI Terminal text color codes
    /**
     * This code used to reset terminal color
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //ANSI Terminal background color codes
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    /**
     * Print new line.
     * @param s String value
     */
    public void println(String s) {
        System.out.println(s);
    }

    /**
     * Print new line with specified color
     * @param s String value
     * @param colorCode The ANSI color value
     */
    public void println(String s,String colorCode) {
        println(colorCode + s +ANSI_RESET);
    }

    /**
     * Print value
     * @param s String value
     */
    public void print(String s) {
        System.out.print(s);
    }

    /**
     * Print value with specified color
     * @param s
     * @param colorCode
     */
    public void print(String s,String colorCode) {
        print(colorCode + s + ANSI_RESET);
    }

    /**
     * Print items with an ordinal number
     * @param items The array list of items
     */
    public void printItems(String[] items) {
        for (int i = 0; i < items.length; i++) {
            println(i + 1 + ". " + items[i]);
        }
    }

    /**
     * Read N user's input form stdin and fill it in {@link Map<String,Object>} object
     * @param stringMap The map that's define how to read. Ex. {"name", "String", "index": "int"}.
     *                  Supported types: string, int, double
     * @return It's a result value that converted to specified type. Ex. {"name": "Danial", "index": 0}
     */
    public Map<String, Object> readParameters(Map<String, String> stringMap) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Object> outputMap = new HashMap<>(stringMap.size());

        String[] keys = stringMap.keySet().toArray(new String[0]);
        String[] values = stringMap.values().toArray(new String[0]);

        for (int i = 0; i < stringMap.size(); i++) {
            print("Enter the " + keys[i] + ": ");
            String userInput = scanner.nextLine();

            try {
                outputMap.put(keys[i],cast(userInput, values[i]));
            } catch (Exception e) {
                println("Invalid Input. Should be " + values[i],ANSI_RED);
                i--;
            }
        }

        return outputMap;
    }

    /**
     * Cast the given object to specified type
     * @param a The object to be cast
     * @param type The type of the object should cast to it.
     *             Supported types: string, int, double
     * @return The {@link Object} can be safely cast to given type. Ex. (int)cast("1","int")
     * @exception Exception if any error happened when try to cast values
     */
    public Object cast(Object a, String type) {
        return switch (type.toLowerCase()) {
            case "string" -> a.toString();
            case "int", "integer" -> Integer.parseInt(a.toString());
            case "double" -> Double.parseDouble(a.toString());
            default -> throw new UnsupportedOperationException("Unsupported type: " + type);
        };
    }
}
