package src.Command;

import src.BaseObjects.*;

import src.User.User;
import src.Utils.HeliosConnectable;
import src.Utils.ManagerOfCollection;
import src.Utils.OutputToXml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class Receiver implements Serializable{
    public final Invoker commandInvoker;
    public final String path = "src\\main\\java\\src\\Command\\ConcreteCommands\\";
    public Properties properties = new Properties();
    public Receiver(Invoker commandInvoker) throws IOException {
        this.commandInvoker = commandInvoker;

    }
    //xml and l10n
    public void help(User user) throws IOException {
        StringJoiner joiner = new StringJoiner("");
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));

        for (Map.Entry<String, Command> commandEntry: commandInvoker.invokerHashMap.entrySet()) {
            String propKey = user.getLanguage() + "." + commandEntry.getValue().writeInfo();
            joiner.add(
                    "<command>" +
                            "<name>" + commandEntry.getKey() + "</name>" +
                            "<description>" + properties.getProperty(propKey) + "</description>" +
                    "</command>"
            );
        }
        String stringOutput = joiner.toString();
        // commandInvoker.invokerHashMap.forEach((name, command) -> command.writeInfo());

        System.out.println("<?xml version=\"1.0\"?><commands>" + stringOutput + "</commands>");
    }

    //xml and l10n
    public void info(User user) {
        String stringOutput = ManagerOfCollection.getInformationAbout(user.getLanguage());
        System.out.println("<?xml version=\"1.0\"?><collectionInfo>" + stringOutput + "</collectionInfo>");
    }

    //xml and l10n
    public void show(User user) {
        TreeSet<SpaceMarine> myCollection = ManagerOfCollection.getMyCollection();
        StringJoiner joiner = new StringJoiner("");

        for (SpaceMarine spaceMarine: myCollection) {
            joiner.add(OutputToXml.marineOutput(spaceMarine, user.getLanguage()));
        }
        String stringOutput = joiner.toString();

        System.out.println("<?xml version=\"1.0\"?><spaceMarines>" + stringOutput + "</spaceMarines>");

    }

    //xml and l10n
    public void add(User user, SpaceMarine spaceMarineFromClient) throws SQLException, ClassNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));

        spaceMarineFromClient.setCreatedBy(user.getLogin());
        Connection con = HeliosConnectable.createConToDB();
        ManagerOfCollection.insertSpaceMarine(spaceMarineFromClient, con);

        Long id = ManagerOfCollection.getCurrentIdInPostgres();

        String propKey = user.getLanguage() + ".Command.Add.execute";
        String stringOutput = properties.getProperty(propKey) + " " + id;

        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

        spaceMarineFromClient.setId(id);
        ManagerOfCollection.add(spaceMarineFromClient);
    }
    // TODO: перетянуть все юзер чеки в менеджер

    //xml and l10n
    public void update(Long id, SpaceMarine spaceMarineFromClient, User user) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));
        String propKey = user.getLanguage() + ".Command.Update.execute.";
        try {
            long ID = id;
            if (ManagerOfCollection.elemExist(ID)) {
                if (Objects.equals(ManagerOfCollection.getElemByID(id).getCreatedBy(), user.getLogin())) {
                    ManagerOfCollection.update(spaceMarineFromClient, ID);

                    propKey += "Success";
                    String stringOutput = properties.getProperty(propKey);

                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

                    ManagerOfCollection.save();

                }
                else {
                    propKey += "Unacceptable";
                    String stringOutput = properties.getProperty(propKey);

                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
                }
            }
            else {
                propKey += "Empty";
                String stringOutput = properties.getProperty(propKey);

                System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
            }
        }
        catch (NumberFormatException e) {
            propKey += "Error";
            String stringOutput = properties.getProperty(propKey);

            System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //xml and l10n
    public void remove_by_id(Long id, User user) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));
        String propKey = user.getLanguage() + ".Command.RemoveById.execute.";
        try {
            long ID = id;
            if (ManagerOfCollection.elemExist(ID)) {
                if (Objects.equals(ManagerOfCollection.getElemByID(id).getCreatedBy(), user.getLogin())) {
                    ManagerOfCollection.remove_by_id(ID);

                    propKey += "Success";
                    String stringOutput = properties.getProperty(propKey) + " " + ID;

                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

                    ManagerOfCollection.save();

                }
                else {
                    propKey += "Unacceptable";
                    String stringOutput = properties.getProperty(propKey);

                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
                }
            } else {
                propKey += "Empty";
                String stringOutput = properties.getProperty(propKey);

                System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
            }
        } catch (NumberFormatException e) {
            propKey += "Error";
            String stringOutput = properties.getProperty(propKey);

            System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //xml and l10n
    public void clear(User user) throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));
        ManagerOfCollection.clear(user);

        String propKey = user.getLanguage() + ".Command.Clear.execute";
        String stringOutput = properties.getProperty(propKey);

        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
        ManagerOfCollection.save();
    }

    public void exit() throws IOException, SQLException, ClassNotFoundException {
//        String stringOutput = "Save you progress in collection? [yes/no]";
//        //System.out.println(stringOutput);
//        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
//
//        Scanner exitScanner = new Scanner(System.in);
//        while (true) {
//            if (exitScanner.hasNextLine()) {
//                String ans = exitScanner.nextLine();
//                if (ans.equals("yes")) {
//                    ManagerOfCollection.save();
//                    break;
//                } else if (ans.equals("no")) {
//                    break;
//                }
//                else {
//                    String stringOutput2 = "Invalid answer. [yes/no]";
//                    //System.out.println(stringOutput);
//                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput2 + "</otvet>");
//                }
//            }
//        }
//
//        System.out.println("Program is ending, bye-bye!");
//        System.exit(0);
    }

    //xml and l10n
    public void remove_greater(SpaceMarine spaceMarineFromClient, User user) throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));
        int removed_items = ManagerOfCollection.remove_greater(spaceMarineFromClient, user);
        String propKey = user.getLanguage() + ".Command.RemoveGreater.execute";

        String stringOutput = removed_items + " " + properties.getProperty(propKey);
        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

        ManagerOfCollection.save();
    }

    //xml and l10n
    public void remove_lower(SpaceMarine spaceMarineFromClient, User user) throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));
        int removed_items = ManagerOfCollection.remove_lower(spaceMarineFromClient, user);

        String propKey = user.getLanguage() + ".Command.RemoveLower.execute";
        String stringOutput = removed_items + " " + properties.getProperty(propKey);
        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

        ManagerOfCollection.save();
    }

    //xml and l10n
    public void save() throws IOException, SQLException, ClassNotFoundException {
        ManagerOfCollection.save();
    }

    //TODO: slomano
    public void history() {
        if (commandInvoker.invokerListOfCommand.size() >= 11) {
            for (int i = commandInvoker.invokerListOfCommand.size() - 1; i > commandInvoker.invokerListOfCommand.size() - 11; i--) {
                System.out.println(commandInvoker.invokerListOfCommand.get(i));
            }
        }
        else {
            System.out.println("There are not enough elements to output the last 11 elements. Current number of items: " + commandInvoker.invokerListOfCommand.size());
        }
    }

    //xml and l10n
    public void remove_all_by_health(Double health, User user) throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));
        double HP = health;

        int removed_items = ManagerOfCollection.remove_all_by_health(HP, user);


        String propKey = user.getLanguage() + ".Command.RemoveAllByHealth.execute";
        String stringOutput = removed_items + " " + properties.getProperty(propKey);
        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

        System.out.println(removed_items + stringOutput);
        ManagerOfCollection.save();
    }

    //xml and l10n
    public void max_by_melee_weapon(User user) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(path + user.getLanguage() + ".properties", StandardCharsets.UTF_8));
        SpaceMarine spaceMarine = ManagerOfCollection.max_by_melee_weapon();
        if (spaceMarine != null) {
            System.out.println(OutputToXml.marineOutput(spaceMarine, user.getLanguage()));
        }
        else {
            String propKey = user.getLanguage() + ".Command.MaxByMeleeWeapon.execute.Error";
            String stringOutput = properties.getProperty(propKey);

            System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
        } // Будет null, если максимального элемента нет
    }

    //xml and l10n
    public void print_unique_chapter() {
        TreeSet<Chapter> uniqueChapters = ManagerOfCollection.print_unique_chapter();
        StringJoiner joiner = new StringJoiner("");

        for (Chapter chapter: uniqueChapters) {
            joiner.add(OutputToXml.chaptersOutput(chapter));
        }

        System.out.println("<chapters>" + joiner + "</chapters>");
    }

}
