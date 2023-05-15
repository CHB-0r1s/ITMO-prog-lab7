package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;

import java.io.IOException;
import java.sql.SQLException;

public class RemoveAllByHealth extends Command {
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public RemoveAllByHealth (Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute() throws IOException, SQLException, ClassNotFoundException {
//        commandReceiver.remove_all_by_health(this.getDoubleFromClient());
        commandReceiver.remove_all_by_health((Double) this.getExtraDataFromClient());
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.remove_all_by_health();
    }

    // TODO: readers wrapper-only
    @Override
    protected void writeInfo() {
        System.out.println("������� remove_all_by_health. ���������: remove_all_by_health health � " +
                "������� �� ��������� ��� ��������, �������� ���� health �������� ������������ ���������.");
    }
}