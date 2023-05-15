package src.Utils.MyReaders;

import java.util.Scanner;

public class MyStringReader {
    public static String read(String consoleMessage, boolean nullable) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(consoleMessage);
        String inputString = scanner.nextLine();

        while(!nullable && inputString.equals("")) {
            System.out.print("This field cannot be empty. " + consoleMessage);
            inputString = scanner.nextLine().trim();
        }

        if(nullable && inputString.equals("")) { inputString = null; }
        return inputString;
    }
}
