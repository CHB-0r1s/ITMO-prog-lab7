package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveLower extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public RemoveLower (Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.remove_lower(this.getSpaceMarineFromClient());
        commandReceiver.remove_lower((SpaceMarine) this.getExtraDataFromClient(), user);
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.remove_lower();
    }

    @Override
    protected String writeInfo() {
        return "The remove_lower command is to remove all items smaller than the specified one from the collection.";
    }
}