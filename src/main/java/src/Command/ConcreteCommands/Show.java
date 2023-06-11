package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class Show extends Command {
    public Show()
    {}

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
    protected String writeInfo() {
        return "Command.Show.writeInfo";
    }
}
