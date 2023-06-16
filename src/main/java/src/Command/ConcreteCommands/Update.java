package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.util.ArrayList;

public class Update extends Command {
    public Update()
    {}

    public Update(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException {
//        commandReceiver.update(this.getLongFromClient(), this.getSpaceMarineFromClient());
//        commandReceiver.update();
        ArrayList<Object> buffer = (ArrayList<Object>) this.getExtraDataFromClient();
        commandReceiver.update((Long) buffer.get(0), (SpaceMarine) buffer.get(1), user);
    }

    @Override
    public Command clientExecute() throws IOException {
        return clientReceiver.update();
    }

    @Override
    protected String writeInfo() {
        return "Command.Update.writeInfo";
    }
}
