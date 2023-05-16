package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;
import java.sql.SQLException;

public class Exit extends Command{
    private final Receiver commandReceiver;
    private final ClientReceiver clientReceiver;

    public Exit(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
        }

        @Override
        public void execute(User user) throws IOException, SQLException, ClassNotFoundException {
            commandReceiver.exit();
        }

    @Override
    public Command clientExecute()
    {
        System.out.println("Program is ending...");
        System.exit(0);
        return clientReceiver.exit();
    }

    @Override
        protected void writeInfo() {
            System.out.println("The exit command terminates the program without saving to a file");
        }


}
