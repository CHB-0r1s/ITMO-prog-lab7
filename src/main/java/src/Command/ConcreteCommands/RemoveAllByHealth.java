package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveAllByHealth extends Command {

    public RemoveAllByHealth()
    {}
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
        return "Command.RemoveAllByHealth.writeInfo";
    }
}