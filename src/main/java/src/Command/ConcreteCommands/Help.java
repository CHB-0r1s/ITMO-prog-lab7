package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class Help extends Command{
        private final Receiver commandReceiver;
        private final ClientReceiver clientReceiver;

    public Help(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) {
        commandReceiver.help();
    }
    @Override
    public Command clientExecute() {
        return clientReceiver.help();

    }

    @Override
    protected String writeInfo() {
        return "The help command displays help for all available commands";
    }
}
