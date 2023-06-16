package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;

public class Help extends Command{
    public Help(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
        super.tableTypeCommand = true;
    }

    public Help()
    {
        super.tableTypeCommand = true;
    }

    @Override
    public void execute(User user) throws IOException {
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
