package src.Command;

import src.BaseObjects.*;

import src.User.User;
import src.Utils.HeliosConnectable;
import src.Utils.ManagerOfCollection;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


public class Receiver implements Serializable{
    public final Invoker commandInvoker;
    public final File file = new File("lab7\\src\\main\\java\\src\\Command\\ConcreteCommands\\eng_lang.properties");
    public Properties properties = new Properties();
    public Receiver(Invoker commandInvoker) throws IOException {
        this.commandInvoker = commandInvoker;
        properties.load(new FileReader(file));
    }

    public void help() {
        StringJoiner joiner = new StringJoiner("");
        //properties.load(new FileReader(file));
        for (Map.Entry<String, Command> commandEntry: commandInvoker.invokerHashMap.entrySet()) {
            joiner.add("<command><name>" + commandEntry.getKey() +
                    "</name><description>" + commandEntry.getValue().writeInfo() +
                    "</description></command>");
        }
        String stringOutput = joiner.toString();
        commandInvoker.invokerHashMap.forEach((name, command) -> command.writeInfo());
        //System.out.println(stringOutput);
        System.out.println("<?xml version=\"1.0\"?><commands>" + stringOutput + "</commands>");
    }

    //xml
    public void info() {
        String stringOutput = ManagerOfCollection.getInformationAbout();
        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
    }

    public void show() {
        ManagerOfCollection.show();
    }
    //xml
    public void add(User user, SpaceMarine spaceMarineFromClient) throws SQLException, ClassNotFoundException {
        spaceMarineFromClient.setCreatedBy(user.getLogin());
        Connection con = HeliosConnectable.createConToDB();
        ManagerOfCollection.insertSpaceMarine(spaceMarineFromClient, con);

        Long id = ManagerOfCollection.getCurrentIdInPostgres();

        String propKey = user.getLanguage() + ".Command.Add.execute";
        String stringOutput = properties.getProperty(propKey) + " " + id;
        //System.out.println(stringOutput);
        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

        spaceMarineFromClient.setId(id);
        ManagerOfCollection.add(spaceMarineFromClient);
    }
    // TODO: перетянуть все юзер чеки в менеджер
    //xml
    public void update(Long id, SpaceMarine spaceMarineFromClient, User user) {
        try {
            long ID = id;
            if (ManagerOfCollection.elemExist(ID)) {
                if (Objects.equals(ManagerOfCollection.getElemByID(id).getCreatedBy(), user.getLogin())) {
                    ManagerOfCollection.update(spaceMarineFromClient, ID);
                    ManagerOfCollection.save();

                    String stringOutput = "Update completed";
                    //System.out.println(stringOutput);
                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
                }
                else {
                    String stringOutput = "You can not modify this object!!!";
                    //System.out.println(stringOutput);
                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
                }
            }
            else {
                String stringOutput = "The item with this ID is not in the collection.";
                //System.out.println(stringOutput);
                System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
            }
        } catch (NumberFormatException e) {
            String stringOutput = "The command is not executed. You have entered an incorrect argument.";
            //System.out.println(stringOutput);
            System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //xml
    public void remove_by_id(Long id, User user) {
        try {
            long ID = id;
            if (ManagerOfCollection.elemExist(ID)) {
                if (Objects.equals(ManagerOfCollection.getElemByID(id).getCreatedBy(), user.getLogin())) {
                    ManagerOfCollection.remove_by_id(ID);
                    String stringOutput = "Element with ID " + ID + " was deleted successfully";
                    //System.out.println(stringOutput);
                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");

                }
                else {
                    String stringOutput = "This user can not modify this object!!!";
                    //System.out.println(stringOutput);
                    System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
                }
            } else {
                String stringOutput = "There is no element with such ID in the collection";
                //System.out.println(stringOutput);
                System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
            }
        } catch (NumberFormatException e) {
            String stringOutput = "The command is not executed. You have entered an incorrect argument.";
            //System.out.println(stringOutput);
            System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
        }
    }

    //xml
    public void clear(User user) throws IOException, SQLException, ClassNotFoundException {
        ManagerOfCollection.clear(user);
        String stringOutput = "All the items available to you have been removed";
        //System.out.println(stringOutput);
        System.out.println("<?xml version=\"1.0\"?><otvet>" + stringOutput + "</otvet>");
        ManagerOfCollection.save();
    }

    //xml
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

    public void remove_greater(SpaceMarine spaceMarineFromClient, User user) throws IOException, SQLException, ClassNotFoundException {
        ManagerOfCollection.remove_greater(spaceMarineFromClient, user);
        ManagerOfCollection.save();
    }

    public void remove_lower(SpaceMarine spaceMarineFromClient, User user) throws IOException, SQLException, ClassNotFoundException {
        ManagerOfCollection.remove_lower(spaceMarineFromClient, user);
        ManagerOfCollection.save();
    }

    public void save() throws IOException, SQLException, ClassNotFoundException {
        ManagerOfCollection.save();
    }

    //slomano
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

    public void remove_all_by_health(Double health, User user) throws IOException, SQLException, ClassNotFoundException {
        double HP = health;

        ManagerOfCollection.remove_all_by_health(HP, user);
        ManagerOfCollection.save();
    }

    public void max_by_melee_weapon() {
        ManagerOfCollection.max_by_melee_weapon();
    }

    public void print_unique_chapter() {
        ManagerOfCollection.print_unique_chapter();
    }

}
