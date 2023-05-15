package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;

public class History extends Command{
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public History(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute() {
        commandReceiver.history();

    }

    @Override
    public Command clientExecute() {
        return clientReceiver.history();
    }

    @Override
    protected void writeInfo() {
        System.out.println("The history command outputs the last 11 commands.");
    }
}
