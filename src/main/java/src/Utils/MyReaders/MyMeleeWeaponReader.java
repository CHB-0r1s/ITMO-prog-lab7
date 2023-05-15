package src.Utils.MyReaders;

import src.BaseObjects.AstartesCategory;
import src.BaseObjects.MeleeWeapon;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class MyMeleeWeaponReader {
    public static boolean checkExist(String toContains) {
        return Arrays.stream(MeleeWeapon.values()).anyMatch((meleeWeapon) -> meleeWeapon.name().equals(toContains));
    }
    public static MeleeWeapon read(String consoleMessage, boolean nullable) {
        Scanner scanner = new Scanner(System.in);  // TODO: inEnum Checking

        System.out.print(consoleMessage + " Choose a melee weapon from the presented("
                + Arrays.asList(MeleeWeapon.values()) + "): ");
        String inputString = "";
        while(!nullable && inputString.equals("")) {
            inputString = scanner.nextLine().trim();
            if (inputString.equals("")) {System.out.print("This field cannot be empty. " + consoleMessage);}
            try {
                Enum.valueOf(MeleeWeapon.class, inputString.toUpperCase(Locale.ROOT));
            }
            catch (Exception ex) {
                System.out.println("Invalid object category name");
                inputString = "";
            }
        }
        return Enum.valueOf(MeleeWeapon.class, inputString.toUpperCase(Locale.ROOT));
    }
}

