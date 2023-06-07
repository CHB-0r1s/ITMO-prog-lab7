package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class Clear extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Clear(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
        commandReceiver.clear(user);
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.clear();
    }

    @Override
    protected String writeInfo() {
        return "The clear command clears the collection.";
    }
}
