package src.Command;

import src.BaseObjects.SpaceMarine;
import src.Command.ConcreteCommands.*;
import src.Utils.SpaceMarineCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;


public class Invoker implements Serializable
{
    public HashMap<String, Command> invokerHashMap = new HashMap<String, Command>();
    public ArrayList<String> invokerListOfCommand = new ArrayList<>();
    private static ArrayList<String> fileNamesForNoRecursion = new ArrayList<>();

    public void invoke(String[] command_name) throws IOException {
        try {
            if (command_name.length > 0) {
                Command command = invokerHashMap.get(command_name[0]);
                invokerListOfCommand.add(command_name[0]);
                command.execute();
            } else {
                System.out.println("You have not entered a command.");
            }
        } catch (IllegalStateException | NullPointerException ex) {
            System.out.println("There is no command " + command_name[0] + ". For reference, use – help");
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Command invokeForClient(String[] command_name) throws IOException {
        fillHashMap();
        if (command_name.length > 0 && invokerHashMap.get(command_name[0]) != null) {
            Command command = invokerHashMap.get(command_name[0]);


            if (command instanceof ExecuteScript) {
                if (command_name.length > 1) {
                    try {
                        SettingExtraDataFromClient.setCommandsFromScript(command_name[1], command);
                        return command;
                    } catch (FileNotFoundException e) {
                        System.out.println("There is no file with such name. Try again.");
                        return null;
                    }
                } else {
                    System.out.println("You have not entered a file name.");
                    return null;
                }
            }

            //i think there must be no code from 42 to 55, but really, it doesn't work from how i try to write it :(

            invokerListOfCommand.add(command_name[0]);

            fillHashMap();
            return command.clientExecute();

        }

        else if (command_name.length == 0) {
            System.out.println("You have not entered a command.");
            return null;
        }
        else {
            System.out.println("There is no command " + command_name[0] + ". For reference, use – help");
            return null;
        }
    }


    public void fillHashMap()
    {
        Receiver commandReceiver = new Receiver(this);
        ClientReceiver clientReceiver = new ClientReceiver(this);
        invokerHashMap.put("help", new Help(commandReceiver, clientReceiver));
        invokerHashMap.put("info", new Info(commandReceiver, clientReceiver));
        invokerHashMap.put("add", new Add(commandReceiver, clientReceiver));
        invokerHashMap.put("show", new Show(commandReceiver, clientReceiver));
        invokerHashMap.put("update", new Update(commandReceiver, clientReceiver));
        invokerHashMap.put("remove_by_id", new RemoveByID(commandReceiver, clientReceiver));
        invokerHashMap.put("clear", new Clear(commandReceiver, clientReceiver));
        invokerHashMap.put("exit", new Exit(commandReceiver, clientReceiver));
        invokerHashMap.put("remove_greater", new RemoveGreater(commandReceiver, clientReceiver));
        invokerHashMap.put("remove_lower", new RemoveLower(commandReceiver, clientReceiver));
        // invokerHashMap.put("save", new Save(commandReceiver));
        invokerHashMap.put("execute_script", new ExecuteScript(commandReceiver));
        invokerHashMap.put("history", new History(commandReceiver, clientReceiver));
        invokerHashMap.put("remove_all_by_health", new RemoveAllByHealth(commandReceiver, clientReceiver));
        invokerHashMap.put("max_by_melee_weapon", new MaxByMeleeWeapon(commandReceiver, clientReceiver));
        invokerHashMap.put("print_unique_chapter", new PrintUniqueChapter(commandReceiver, clientReceiver));
    }
}
