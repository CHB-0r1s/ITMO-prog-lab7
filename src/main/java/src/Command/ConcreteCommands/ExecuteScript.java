package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import src.Command.ClientReceiver;
import src.User.User;

public class ExecuteScript extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver = null;
    private static String path;

    public ExecuteScript(Receiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public void execute(User user) throws StackOverflowError, IOException, SQLException, ClassNotFoundException {
        ArrayList<Command> commands = (ArrayList<Command>) this.getExtraDataFromClient();
        for (Command value : commands) {
            value.execute(user);
        }
    }

    @Override
    public Command clientExecute() {

        return clientReceiver.execute_script(getFileName());
    }
    private String getFileName()
    {
        System.out.println("Write file name:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    protected String writeInfo() {
        return "The execute_script command. Syntax: execute_script file_name – read and execute the script from the specified file. " +
                "The script contains commands in the same form as they are entered by the user in interactive mode.";
    }

    public static String getPath() {
        return path;
    }

}
