package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class Add extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Add(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.add(this.getSpaceMarineFromClient());
        commandReceiver.add((SpaceMarine) this.getExtraDataFromClient(), user);
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.add();
    }

    @Override
    protected String writeInfo() {
        return "The add command adds a new item to the collection";
    }
}
