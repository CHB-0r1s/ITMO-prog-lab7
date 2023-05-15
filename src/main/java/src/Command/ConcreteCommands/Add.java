package src.Command.ConcreteCommands;

import src.BaseObjects.SpaceMarine;
import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;

import java.io.IOException;
import java.sql.SQLException;

public class Add extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Add(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute() throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.add(this.getSpaceMarineFromClient());
        commandReceiver.add((SpaceMarine) this.getExtraDataFromClient());
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.add();
    }

    @Override
    protected void writeInfo() {
        System.out.println("The add command adds a new item to the collection");
    }
}
