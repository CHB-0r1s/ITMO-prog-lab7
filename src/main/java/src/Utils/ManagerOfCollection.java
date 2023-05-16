package src.Utils;

import java.io.*;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import src.BaseObjects.*;

public class ManagerOfCollection implements HeliosConnectable{
    private static boolean dbIsInit = false;
    private static TreeSet<SpaceMarine> myCollection;
    private static ZonedDateTime dateOfCreate;
    public static void initDB() throws ClassNotFoundException, SQLException {
        Connection con = HeliosConnectable.createConToDB();
        Statement crt = con.createStatement();
        crt.executeUpdate("create table if not exists spacemarine" +
                "(id int, name text, x decimal, y decimal, " +
                "creationDate TIMESTAMP WITH TIME ZONE, health decimal, " +
                "astartesCategory text, weapon text, meleeWeapon text, " +
                "chapterName text, parentLegion text)");
        con.close();
        ManagerOfCollection.dbIsInit = true;
    }

    public static void insertSpaceMarine(SpaceMarine spaceMarine, Connection con) throws SQLException {
        PreparedStatement insSt = con.prepareStatement("insert into spacemarine " +
                "(id, name, x, y, creationDate, health, astartesCategory, weapon, meleeWeapon, chapterName, parentLegion) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        insSt.setLong(1, spaceMarine.getId());
        insSt.setString(2, spaceMarine.getName());
        insSt.setFloat(3, spaceMarine.getCoordinates().getX());
        insSt.setDouble(4, spaceMarine.getCoordinates().getY());
        insSt.setObject(5, Timestamp.from(spaceMarine.getCreationDate().toInstant()));
        insSt.setFloat(6, spaceMarine.getHealth());
        insSt.setString(7, spaceMarine.getCategory().toString());
        insSt.setString(8, spaceMarine.getWeaponType().toString());
        insSt.setString(9, spaceMarine.getMeleeWeapon().toString());
        insSt.setString(10, spaceMarine.getChapter().getName());
        insSt.setString(11, spaceMarine.getChapter().getParentLegion());
        insSt.execute();
    }

    public static SpaceMarine rowToSpaceMarine(ResultSet spaceMarineRow) throws SQLException {
        Long id = spaceMarineRow.getLong(1);
        String name = spaceMarineRow.getString(2);
        Float x = spaceMarineRow.getFloat(3);
        double y = spaceMarineRow.getDouble(4);
        ZonedDateTime creationDate = (ZonedDateTime) spaceMarineRow.getObject(5);
        float health = spaceMarineRow.getFloat(6);
        String category = spaceMarineRow.getString(7);
        String weaponType = spaceMarineRow.getString(8);
        String meleeWeapon = spaceMarineRow.getString(9);
        String chapterName = spaceMarineRow.getString(10);
        String chapterParentLegion = spaceMarineRow.getString(11);
        return new SpaceMarine(id, name, new Coordinates(x, y), creationDate, health,
                Enum.valueOf(AstartesCategory.class, category.toUpperCase(Locale.ROOT)),
                Enum.valueOf(Weapon.class, weaponType.toUpperCase(Locale.ROOT)),
                Enum.valueOf(MeleeWeapon.class, meleeWeapon.toUpperCase(Locale.ROOT)),
                new Chapter(chapterName, chapterParentLegion));

    }

    public static void createMyCollection() {
        if (myCollection == null) {
            myCollection = new TreeSet<>();
            dateOfCreate = ZonedDateTime.now();
        }
    }

    public static void getInformationAbout() {
        System.out.println("Information about the collection:");
        System.out.println("Collection Type - " + myCollection.getClass().getName());
        System.out.println("Date the collection was created - " + dateOfCreate);
        System.out.println("Number of elements - " + myCollection.size());
        System.out.println("_________________________________________________________\n");
    }

    public static void add(SpaceMarine spaceMarine) {
        myCollection.add(spaceMarine);
    }

    public static TreeSet<SpaceMarine> getMyCollection() {
        return myCollection;
    }

    public static void show() {
        for (SpaceMarine spaceMarine: myCollection) {
            System.out.println("Object ID - " + spaceMarine.getId());
            System.out.println("Object name - " + spaceMarine.getName());
            System.out.println("The X coordinate of the object - " + spaceMarine.getCoordinates().getX());
            System.out.println("The Y coordinate of the object - " + spaceMarine.getCoordinates().getY());
            System.out.println("Date and time of object creation - " + spaceMarine.getCreationDate());
            System.out.println("Object health - " + spaceMarine.getHealth());
            System.out.println("Object category - " + spaceMarine.getCategory());
            System.out.println("Object's weapon - " + spaceMarine.getWeaponType());
            System.out.println("Melee weapons of the object - " + spaceMarine.getMeleeWeapon());
            System.out.println("Location of the object - " + spaceMarine.getChapter().getName() + ":" + spaceMarine.getChapter().getParentLegion());
            System.out.println("_________________________________________________________\n");
        }
    }

    public static void update(SpaceMarine marineToUpdate, long elementId) {
        myCollection.forEach(spaceMarine -> {
            if (spaceMarine.getId() == elementId) {
                spaceMarine.setName(marineToUpdate.getName());
                spaceMarine.setCoordinates(marineToUpdate.getCoordinates());
                spaceMarine.setHealth(marineToUpdate.getHealth());
                spaceMarine.setCategory(marineToUpdate.getCategory());
                spaceMarine.setWeaponType(marineToUpdate.getWeaponType());
                spaceMarine.setMeleeWeapon(marineToUpdate.getMeleeWeapon());
                spaceMarine.setChapter(marineToUpdate.getChapter());
            }
        });
    }

    public static boolean elemExist(long ID) {
        for (SpaceMarine spaceMarine: ManagerOfCollection.getMyCollection()) {
            if (spaceMarine.getId() == ID) {
                return spaceMarine.getId() == ID;
            }
        }
        return false;
    }

    public static void remove_by_id(long ID) {
        myCollection.forEach(
                spaceMarine -> {
                    if (spaceMarine.getId() == ID) {
                        myCollection.remove(spaceMarine);
                    }
                }
        );
    }

    public static void clear() {
        myCollection.clear();
    }

    public static Long maxID() {
        AtomicLong x = new AtomicLong();
        myCollection.forEach(
                spaceMarine -> {
                    if (spaceMarine.getId() > x.get()) {
                        x.set(spaceMarine.getId());
                    }
                }
        );
        return x.longValue();
    }

    public static void remove_greater(SpaceMarine spaceMarine) {
        AtomicInteger x = new AtomicInteger();

        myCollection.forEach(
                spaceMarine1 -> {
                    if (spaceMarine1.compareTo(spaceMarine) > 0) {
                        myCollection.remove(spaceMarine1);
                        x.getAndIncrement();
                    }
                }
        );
        System.out.println(x + "items found and removed.");
    }

    public static void remove_lower(SpaceMarine spaceMarine) {
        AtomicInteger x = new AtomicInteger();

        myCollection.forEach(
                spaceMarine1 -> {
                    if (spaceMarine1.compareTo(spaceMarine) < 0) {
                        myCollection.remove(spaceMarine1);
                        x.getAndIncrement();
                    }
                }
        );
        System.out.println(x + "items found and removed.");
    }

    public static void  save() throws IOException, ClassNotFoundException, SQLException {
        if (!ManagerOfCollection.dbIsInit) {
            initDB();
        }
        try {
            Connection con = HeliosConnectable.createConToDB();
            Statement delSt = con.createStatement();
            delSt.executeUpdate("delete from spacemarine where id > 0");
            for (SpaceMarine spaceMarine: ManagerOfCollection.getMyCollection()) {
                insertSpaceMarine(spaceMarine, con);
            }
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void remove_all_by_health(double HP) {
        AtomicInteger x = new AtomicInteger();
        myCollection.forEach(
                spaceMarine -> {
                    if (spaceMarine.getHealth() == HP) {
                        myCollection.remove(spaceMarine);
                        x.getAndIncrement();
                    }
                }
        );
        System.out.println(x + "elements found and removed.");
    }

    public static void max_by_melee_weapon() {
        MeleeWeapon[] list = MeleeWeapon.values();
        int maxLength = 0;
        MeleeWeapon maxMeleeWeapon = null;

        for (MeleeWeapon w: list) {
            if (maxLength < w.toString().length()) {
                maxLength = w.toString().length();
                maxMeleeWeapon = w;
            }
        }
        MeleeWeapon finalMaxMeleeWeapon = maxMeleeWeapon;
        System.out.println(finalMaxMeleeWeapon);
        for (SpaceMarine spaceMarine: myCollection) {
            assert finalMaxMeleeWeapon != null;
            if (Objects.equals(spaceMarine.getMeleeWeapon().toString(), finalMaxMeleeWeapon.toString())) {
                System.out.println("Object's ID - " + spaceMarine.getId());
                System.out.println("Object's name - " + spaceMarine.getName());
                System.out.println("The X coordinate of the object - " + spaceMarine.getCoordinates().getX());
                System.out.println("The Y coordinate of the object -" + spaceMarine.getCoordinates().getY());
                System.out.println("Date and time of object creation - " + spaceMarine.getCreationDate());
                System.out.println("Object's health - " + spaceMarine.getHealth());
                System.out.println("Object's category - " + spaceMarine.getCategory());
                System.out.println("Object's weapon - " + spaceMarine.getWeaponType());
                System.out.println("Melee weapons of the object - " + spaceMarine.getMeleeWeapon());
                System.out.println("Location of the object - " + spaceMarine.getChapter().getName() + ":" + spaceMarine.getChapter().getParentLegion());
                System.out.println("_________________________________________________________\n");
                break;
            }
        }
    }

    public static void print_unique_chapter() {
        TreeSet<Chapter> setOfChapter = new TreeSet<>();
        for (SpaceMarine spaceMarine: myCollection) {
            setOfChapter.add(spaceMarine.getChapter());
        }

        for (Chapter chapter: setOfChapter) {
            System.out.println("name of the unique chapter:" + chapter.getName());
            System.out.println("parentLegion of the unique chapter: " + chapter.getParentLegion());

        }
    }

    public static void fillFromPostgres() throws SQLException, ClassNotFoundException {
        if (!ManagerOfCollection.dbIsInit) {
            initDB();
        }
        Connection con = HeliosConnectable.createConToDB();
        Statement selSt = con.createStatement();
        ResultSet spaceMarineRow = selSt.executeQuery("select * from spacemarine");
        while (spaceMarineRow.next()) {
            myCollection.add(rowToSpaceMarine(spaceMarineRow));
        }
    }
}