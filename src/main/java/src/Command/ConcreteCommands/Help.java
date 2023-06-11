package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class Help extends Command{
    public Help(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    public Help()
    {}

    @Override
    public void execute(User user) {
        super.commandReceiver.help(user);
    }
    @Override
    public Command clientExecute() {
        return super.clientReceiver.help();
    }

    @Override
    protected String writeInfo() {
        return "Command.Help.writeInfo";
    }
}
