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
    private static String path;
    public ExecuteScript()
    {}

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
    public Command clientExecute() throws IOException {

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
        return "Command.ExecuteScript.writeInfo";
    }

    public static String getPath() {
        return path;
    }

}
