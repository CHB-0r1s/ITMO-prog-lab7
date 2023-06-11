package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveLower extends Command {

    public RemoveLower()
    {}
    public RemoveLower (Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.remove_lower(this.getSpaceMarineFromClient());
        commandReceiver.remove_lower((SpaceMarine) this.getExtraDataFromClient(), user);
    }

    @Override
    public Command clientExecute() throws IOException {
        return clientReceiver.remove_lower();
    }

    @Override
    protected String writeInfo() {
        return "Command.RemoveLower.writeInfo";
    }
}