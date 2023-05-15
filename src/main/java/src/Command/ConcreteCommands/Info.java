package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;

public class Info extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Info(Receiver commandReceiver, ClientReceiver clientReceiver) {

        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute() {
        commandReceiver.info();
    }

    @Override
    public Command clientExecute() { return clientReceiver.info(); }

    @Override
    protected void writeInfo() {
        System.out.println("The info command displays information about the collection (type, initialization date, number of items, etc.)");
    }
}
