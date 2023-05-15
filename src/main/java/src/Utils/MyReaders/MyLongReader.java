package src.Utils.MyReaders;

import java.util.Scanner;

public class MyLongReader {
    public static long read(String consoleMessage) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(consoleMessage);
        long result;
        while (true) {
            try {
                result = Long.parseLong(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException ex) {
                System.out.print("You have to enter a number (long), try again: ");
            }
        }
        return result;
    }
}
