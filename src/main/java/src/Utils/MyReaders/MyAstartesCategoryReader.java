package src.Utils.MyReaders;

import src.BaseObjects.AstartesCategory;
import jdk.jshell.EvalException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MyAstartesCategoryReader {
    public static boolean checkExist(String toContains) {
        return Arrays.stream(AstartesCategory.values()).anyMatch((astartesCategory) -> astartesCategory.name().equals(toContains));
    }
    public static AstartesCategory read(String consoleMessage, boolean nullable) {
        Scanner scanner = new Scanner(System.in);  // TODO: Проверка вхождения в енум

            System.out.print(consoleMessage + " Select a category from the presented("
                + Arrays.asList(AstartesCategory.values()) + "): ");
        String inputString = "";
        while(!nullable && inputString.equals("")) {
            inputString = scanner.nextLine().trim();
            if (inputString.equals("")) {System.out.print("This field cannot be empty. " + consoleMessage);}
            try {
                Enum.valueOf(AstartesCategory.class, inputString.toUpperCase(Locale.ROOT));
            }
            catch (Exception ex) {
                System.out.println("Invalid object category name");
                inputString = "";
            }
        }
        return Enum.valueOf(AstartesCategory.class, inputString.toUpperCase(Locale.ROOT));
    }
}



