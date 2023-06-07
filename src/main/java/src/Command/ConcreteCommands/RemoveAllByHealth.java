package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveAllByHealth extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public RemoveAllByHealth (Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.remove_all_by_health(this.getDoubleFromClient());
        commandReceiver.remove_all_by_health((Double) this.getExtraDataFromClient(), user);
    }

    @Override
    public Command clientExecute() throws IOException {
        return clientReceiver.remove_all_by_health();
    }

    // TODO: readers wrapper-only
    @Override
    protected String writeInfo() {
        return "The remove_all_by_health command. Syntax: remove_all_by_health health – " +
                "remove from the collection all elements whose value of the health field is equivalent to the specified one.";
    }
}