package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class Show extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Show(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) {
        commandReceiver.show();
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.show();
    }

    @Override
    protected void writeInfo() {
        System.out.println("The show command outputs all the elements of the collection in a string representation");
    }
}
