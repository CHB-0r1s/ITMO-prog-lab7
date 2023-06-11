package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class Add extends Command {

    public Add(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    public  Add()
    {}

    @Override
    public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.add(this.getSpaceMarineFromClient());
        commandReceiver.add(user, (SpaceMarine) this.getExtraDataFromClient());
    }

    @Override
    public Command clientExecute() throws IOException {
        return clientReceiver.add();
    }

    @Override
    protected String writeInfo() {
        return "Command.Add.writeInfo";
    }
}
