package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveGreater extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public RemoveGreater (Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.remove_greater(this.getSpaceMarineFromClient());
        commandReceiver.remove_greater((SpaceMarine) this.getExtraDataFromClient(), user);
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.remove_greater();
    }

    @Override
    protected String writeInfo() {
        return "The remove_greater command is to remove all items from the collection that exceed the specified one.";
    }
}