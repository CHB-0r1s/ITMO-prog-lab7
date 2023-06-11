package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

public class PrintUniqueChapter extends Command{

    public PrintUniqueChapter()
    {}
    public PrintUniqueChapter(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) {
        commandReceiver.print_unique_chapter();

    }

    @Override
    public Command clientExecute() {
        return clientReceiver.print_unique_chapter();
    }

    @Override
    protected String writeInfo() {
        return "Command.PrintUniqueChapter.writeInfo";
    }
}
