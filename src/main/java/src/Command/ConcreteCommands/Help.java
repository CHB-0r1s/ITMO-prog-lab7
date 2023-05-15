package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;

public class Help extends Command{
        private final Receiver commandReceiver;
        private final ClientReceiver clientReceiver;

    public Help(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute() {
        commandReceiver.help();
    }
    @Override
    public Command clientExecute() {
        return clientReceiver.help();

    }

    @Override
    protected void writeInfo() {
        System.out.println("The help command displays help for all available commands");
    }
}
