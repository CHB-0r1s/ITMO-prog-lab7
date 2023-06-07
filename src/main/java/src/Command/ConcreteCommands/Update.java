package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.util.ArrayList;

public class Update extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Update(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) {
//        commandReceiver.update(this.getLongFromClient(), this.getSpaceMarineFromClient());
//        commandReceiver.update();
        ArrayList<Object> buffer = (ArrayList<Object>) this.getExtraDataFromClient();
        commandReceiver.update((Long) buffer.get(0), (SpaceMarine) buffer.get(1), user);
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.update();
    }

    @Override
    protected String writeInfo() {
        return "The update command updates the value of the collection element whose id is equal to the specified one.";
    }
}
