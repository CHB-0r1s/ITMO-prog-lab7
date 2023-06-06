package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class Info extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Info(Receiver commandReceiver, ClientReceiver clientReceiver) {

        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) {
        commandReceiver.info();
    }

    @Override
    public Command clientExecute() { return clientReceiver.info(); }

    @Override
    protected String writeInfo() {
        return "The info command displays information about the collection (type, initialization date, number of items, etc.)";
    }
}
