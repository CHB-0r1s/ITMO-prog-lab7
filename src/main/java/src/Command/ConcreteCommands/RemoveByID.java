package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;

public class RemoveByID extends Command {

    public RemoveByID()
    {}
    public RemoveByID (Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) {
//        commandReceiver.remove_by_id(this.getLongFromClient());
        commandReceiver.remove_by_id((Long) this.getExtraDataFromClient(), user);
    }

    @Override
    public Command clientExecute() throws IOException {
        return clientReceiver.remove_by_id();
    }

    @Override
    protected String writeInfo() {
        return "Command.RemoveByID.writeInfo";
    }
}