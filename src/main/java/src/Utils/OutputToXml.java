package src.Utils;

import src.BaseObjects.Chapter;
import src.BaseObjects.SpaceMarine;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.TreeSet;

public class OutputToXml {
    public static String marineOutput(SpaceMarine spaceMarine, String language) {
        StringJoiner joiner = new StringJoiner("");

        joiner.add("<id>" + spaceMarine.getId() + "</id>");
        joiner.add("<name>" + spaceMarine.getName() + "</name>");
        joiner.add("<x>" + spaceMarine.getCoordinates().getX() + "</x>");
        joiner.add("<y>" + spaceMarine.getCoordinates().getY() + "</y>");
        joiner.add("<creationDate>" + spaceMarine.getCreationDate().format(getDFormat(language)) + "</creationDate>");
        joiner.add("<health>" + spaceMarine.getHealth() + "</health>");
        joiner.add("<category>" + spaceMarine.getCategory() + "</category>");
        joiner.add("<weaponType>" + spaceMarine.getWeaponType() + "</weaponType>");
        joiner.add("<meleeWeapon>" + spaceMarine.getMeleeWeapon() + "</meleeWeapon>");
        joiner.add("<chapterName>" + spaceMarine.getChapter().getName() + "</chapterName>");
        joiner.add("<parentLegion>" + spaceMarine.getChapter().getParentLegion() + "</parentLegion>");
        joiner.add("<createdBy>" + spaceMarine.getCreatedBy() + "</createdBy>");

        return "<spaceMarine>" + joiner + "</spaceMarine>";
    }

    public static String chaptersOutput(Chapter chapter) {
        StringJoiner joiner = new StringJoiner("");
        joiner.add("<chapterName>" + chapter.getName() + "</chapterName>");
        joiner.add("<parentLegion>" + chapter.getParentLegion() + "</parentLegion>");

        return "<chapter>" + joiner + "</chapter>";
    }

    public static DateTimeFormatter getDFormat(String language) {
        return switch (language) {
            case "Eng" -> DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a", new Locale("en", "GB"));
            case "Rus" -> DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", new Locale("ru"));
            case "Mak" -> DateTimeFormatter.ofPattern("dd.M.yyyy HH:mm:ss", new Locale("mk", "MK"));
            case "Ven" -> DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm:ss", new Locale("hu", "HU"));
            default -> null;
        };
    }
}
