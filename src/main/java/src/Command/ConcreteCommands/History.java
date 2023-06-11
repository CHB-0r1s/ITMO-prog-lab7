package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class History extends Command{
    public History()
    {}

    public History(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) {
        commandReceiver.history();

    }

    @Override
    public Command clientExecute() {
        return clientReceiver.history();
    }

    @Override
    protected String writeInfo() {
        return "The history command outputs the last 11 commands.";
    }
}
