package src.Command;

import src.BaseObjects.SpaceMarine;
import src.Utils.SpaceMarineCreator;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class ClientReceiver implements Serializable {
    private final Invoker commandInvoker;

    public ClientReceiver(Invoker commandInvoker) {
        this.commandInvoker = commandInvoker;
    }

    public Command help() {
        return commandInvoker.invokerHashMap.get("help");
    }
    public Command info() {
        return commandInvoker.invokerHashMap.get("info");
    }
    public Command show() {
        return commandInvoker.invokerHashMap.get("show");
    }
    public Command clear() {
        return commandInvoker.invokerHashMap.get("clear");
    }
    public Command history() {
        return commandInvoker.invokerHashMap.get("history");
    }
    public Command max_by_melee_weapon() {
        return commandInvoker.invokerHashMap.get("max_by_melee_weapon");
    }
    public Command print_unique_chapter() {
        return commandInvoker.invokerHashMap.get("print_unique_chapter");
    }
    public Command add() throws IOException {
        commandInvoker.fillHashMap();
        Command command = commandInvoker.invokerHashMap.get("add");
        SettingExtraDataFromClient.setSpaceMarineFromClient(command);
        return command;
    }

    public Command update() throws IOException {
        commandInvoker.fillHashMap();
        Command command = commandInvoker.invokerHashMap.get("update");
        SettingExtraDataFromClient.setLongAndSpaceMarine(command);
        return command;
    }

    public Command remove_by_id() throws IOException {
        commandInvoker.fillHashMap();
        Command command = commandInvoker.invokerHashMap.get("remove_by_id");
        SettingExtraDataFromClient.setLongFromClient(command);
        return command;
    }

    public Command exit() {
        return commandInvoker.invokerHashMap.get("exit");

    }

    public Command remove_greater() throws IOException {
        commandInvoker.fillHashMap();
        Command command = commandInvoker.invokerHashMap.get("remove_greater");
        SettingExtraDataFromClient.setSpaceMarineFromClient(command);
        return command;
    }

    public Command remove_lower() throws IOException {
        commandInvoker.fillHashMap();
        Command command = commandInvoker.invokerHashMap.get("remove_lower");
        SettingExtraDataFromClient.setSpaceMarineFromClient(command);
        return command;
    }

    public Command execute_script(String path) throws IOException {
        commandInvoker.fillHashMap();
        Command command = commandInvoker.invokerHashMap.get("execute_script");
        try {
            SettingExtraDataFromClient.setCommandsFromScript(path,command);
        }
        catch (FileNotFoundException e) {
            System.out.println("There is no file with such name.");
        }
        return command;
    }

    private static ArrayList<String> fileNamesForNoRecursion = new ArrayList<>();

    public ArrayList<Command> getCommandsFromScript (String fileName) throws IOException
    {
        fileNamesForNoRecursion.add(fileName);
        File file = new File(fileName);
        Scanner reader = new Scanner(file);
        String line;
        String commandLine;
        ArrayList<String> parameters = new ArrayList<>();
        ArrayList<Command> commands = new ArrayList<>();
        Invoker invoker = commandInvoker;
        SpaceMarine spaceMarine = null;
        Command command = null;
        invoker.fillHashMap();
        outer:
        while (reader.hasNextLine())
        {
            line = reader.nextLine();
            commandLine = line;
            if (line.split(" ")[0].matches("add|update|remove_greater|remove_lower"))
            {
                for (int i = 0; i < 9; i++)
                {
                    if (reader.hasNextLine())
                    {
                        line = reader.nextLine();
                        parameters.add(line);
                    }
                    else
                    {
                        System.out.println("There are not enough parameters to create an object.");
                        break outer;
                    }
                }
                spaceMarine = SpaceMarineCreator.createScriptSpaceMarine(parameters);
                parameters.clear();
            }
            command = invoker.invokerHashMap.get(commandLine.split(" ")[0]);
            invoker.fillHashMap();
            switch (command.getClass().getSimpleName())
            {
                case "Add":
                case "RemoveGreater":
                case "RemoveLower":
                    SettingExtraDataFromClient.setSpaceMarineFromClient(spaceMarine, command);
                    break;
                case "Update": try
                {
//                    command.setLongFromClient(Long.parseLong(commandLine.split(" ")[1]));
                    SettingExtraDataFromClient.setLongFromClient(Long.parseLong(commandLine.split(" ")[1]), command);
                    SettingExtraDataFromClient.setSpaceMarineFromClient(spaceMarine, command);
                    break;
                }catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
                {
                    System.out.println("There is no number after Update.");
                    break outer;
                }

                case "RemoveAllByHealth": try
                {
//                    command.setDoubleFromClient(Double.parseDouble(commandLine.split(" ")[1]));
                    SettingExtraDataFromClient.setDoubleFromClient(Double.parseDouble(commandLine.split(" ")[1]), command);
                }catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
                {
                    System.out.println("There is no number after Remove_All_By_Health.");
                    break outer;
                }

                case "RemoveByID": try
                {
//                    command.setLongFromClient(Long.parseLong(commandLine.split(" ")[1]));
                    SettingExtraDataFromClient.setLongFromClient(Long.parseLong(commandLine.split(" ")[1]), command);
                    SettingExtraDataFromClient.setSpaceMarineFromClient(spaceMarine, command);
                    break;
                }catch (NumberFormatException | ArrayIndexOutOfBoundsException e)
                {
                    System.out.println("There is no number after Update.");
                    break outer;
                }

                case "ExecuteScript":
                    try
                    {
                        if (line.split(" ").length > 1)
                        {
                            if (fileNamesForNoRecursion.contains(line.split(" ")[1]))
                            {
                                System.out.println("There is a recursion in the script. It will be skipped.");
                                command = null;
                            }
                            else
                            {
                                command = invoker.invokeForClient(line.split(" "));
                            }
                        }else
                        {
                            System.out.println("There is no file name after Execute_Script.");
                            command = null;
                        }
                    }catch (IOException e)
                    {
                        System.out.println(e);
                    }

            }
            commands.add(command);
        }
        commands.removeAll(Collections.singleton(null));
        fileNamesForNoRecursion.remove(fileNamesForNoRecursion.size()-1);
        return getListInsideExecuteScript(commands);
    }

    private ArrayList<Command> getListInsideExecuteScript(ArrayList<Command> commands)
    {
        ArrayList<Command> buffCommands = new ArrayList<>();
        for(Command command : commands)
        {
            if (command.getClass().getSimpleName().equals("ExecuteScript"))
            {
                ArrayList<Command> recursiveListCommand = getListInsideExecuteScript((ArrayList<Command>) command.getExtraDataFromClient());
                for (Command com : recursiveListCommand)
                {
                    buffCommands.add(com);
                }
            } else
            {
                buffCommands.add(command);
            }
        }
        return buffCommands;
    }


    public Command remove_all_by_health() throws IOException {
        commandInvoker.fillHashMap();
        Command command = commandInvoker.invokerHashMap.get("remove_all_by_health");
        SettingExtraDataFromClient.setDoubleFromClient(command);
        return command;
    }
}
