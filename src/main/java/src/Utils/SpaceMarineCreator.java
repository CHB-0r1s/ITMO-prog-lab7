package src.Utils;

import src.BaseObjects.*;
import src.Utils.MyReaders.*;

import java.util.ArrayList;

public class SpaceMarineCreator {
    public static SpaceMarine createSpaceMarine() {
        String name = MyStringReader.read("Enter the name of the object ", false);
        Float x = MyFloatReader.read("Enter the x (float) coordinate ");
        double y = MyDoubleReader.read("Enter the y (double) coordinate ");
        float health = MyFloatReader.read("Enter the health of the object (float) ", ">0");
        AstartesCategory astartesCategory = MyAstartesCategoryReader.read("Enter the category of the object. ", false);
        Weapon weapon = MyWeaponReader.read("Enter the type of weapon", true);
        MeleeWeapon meleeWeapon = MyMeleeWeaponReader.read("Enter the type of melee weapon", false);
        String chapterName = MyStringReader.read("Enter the name of the part ", false);
        String parentLegion = MyStringReader.read("Enter the parentLegion name ", true);

        return new SpaceMarine(name, new Coordinates(x, y), health, astartesCategory, weapon, meleeWeapon, new Chapter(chapterName, parentLegion));
    }

    public static SpaceMarine createScriptSpaceMarine(ArrayList<String> parameters) {
        if (validateArray(parameters)) {
            AstartesCategory astartesCategory = null;
            Weapon weapon = null;
            Chapter chapter = new Chapter(null, null);
            if (!parameters.get(4).isEmpty()) { astartesCategory = AstartesCategory.valueOf(parameters.get(4)); }
            if (!parameters.get(5).isEmpty()) { weapon = Weapon.valueOf(parameters.get(5)); }
            if (!parameters.get(7).isEmpty()) { chapter.setName(String.valueOf(parameters.get(7))); }
            if (!parameters.get(8).isEmpty()) { chapter.setParentLegion(String.valueOf(parameters.get(8))); }
            return new SpaceMarine(parameters.get(0),
                    new Coordinates(Float.parseFloat(parameters.get(1)), Double.parseDouble(parameters.get(2))),
                    Float.parseFloat(parameters.get(3)),
                    astartesCategory,
                    weapon,
                    MeleeWeapon.valueOf(parameters.get(6)),
                    chapter);
        } else { System.out.println("One of the parameters does not meet the requirements."); }

        return null;
    }
    private static boolean validateArray(ArrayList<String> parameters) {
        try {
            return !parameters.get(0).isEmpty()
                    && Float.parseFloat(parameters.get(1)) < Float.MAX_VALUE
                    && Double.parseDouble(parameters.get(2)) < Double.MAX_VALUE
                    && Float.parseFloat(parameters.get(3)) > 0
                    && (MyAstartesCategoryReader.checkExist(parameters.get(4)) || parameters.get(4).isEmpty())
                    && (MyWeaponReader.checkExist(parameters.get(5)) || parameters.get(5).isEmpty())
                    && !parameters.get(6).isEmpty();

        } catch (NumberFormatException ex) { return false; }
    }
}
