package src.Utils.MyReaders;

import src.BaseObjects.AstartesCategory;
import src.BaseObjects.Weapon;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MyWeaponReader {
    public static boolean checkExist(String toContains) {
        return Arrays.stream(Weapon.values()).anyMatch((weapon) -> weapon.name().equals(toContains));
    }
    public static Weapon read(String consoleMessage, boolean nullable) {
        Scanner scanner = new Scanner(System.in);

        System.out.print(consoleMessage + " Select a category from the presented("
                + Arrays.asList(Weapon.values()) + "): ");
        String inputString = "";
        while(inputString.equals("")) {
            inputString = scanner.nextLine().trim();
            if (inputString.equals("") && !nullable) {System.out.print("This field cannot be empty." + consoleMessage);}
            else {
                if (inputString.equals("")) {return Weapon.NULL;}
                try {
                    Enum.valueOf(Weapon.class, inputString.toUpperCase(Locale.ROOT));
                }
                catch (Exception ex) {
                    System.out.println("Invalid object category name");
                    inputString = "";
                }
            }
        }
        return Enum.valueOf(Weapon.class, inputString.toUpperCase(Locale.ROOT));
    }
}

