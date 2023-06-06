package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;

import java.io.IOException;
import java.sql.SQLException;
import src.User.User;

public class Save extends Command {
    private final Receiver commandReceiver;

    public Save(Receiver commandReceiver) {
        this.commandReceiver = commandReceiver;
    }

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
        commandReceiver.save();
    }

    @Override
    public Command clientExecute() {
        return null;
    }

    @Override
    protected String writeInfo() {
        return "The save command is to save the collection to a file.";
    }
}