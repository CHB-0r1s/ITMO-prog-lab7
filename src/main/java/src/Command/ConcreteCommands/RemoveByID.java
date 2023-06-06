package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class RemoveByID extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

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
    public Command clientExecute() {
        return clientReceiver.remove_by_id();
    }

    @Override
    protected String writeInfo() {
        return "The remove_by_id command. Syntax: remove_by_id id – remove an item from the collection by its id.";
    }
}