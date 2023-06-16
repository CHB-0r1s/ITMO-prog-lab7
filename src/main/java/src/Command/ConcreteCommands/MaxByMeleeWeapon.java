package src.Command.ConcreteCommands;

import src.Command.Command;
import src.Command.Receiver;
import src.Command.ClientReceiver;
import src.User.User;

import java.io.IOException;

public class MaxByMeleeWeapon extends Command{
    public MaxByMeleeWeapon()
    {}
    public MaxByMeleeWeapon(Receiver commandReceiver, ClientReceiver clientReceiver) {
        this.commandReceiver = commandReceiver;
        this.clientReceiver = clientReceiver;
    }

    @Override
    public void execute(User user) throws IOException {
        commandReceiver.max_by_melee_weapon(user);
    }

    @Override
    public Command clientExecute() {
        return clientReceiver.max_by_melee_weapon();
    }

    @Override
    protected String writeInfo() {
        return "Command.MaxByMeleeWeapon.writeInfo";
    }
}
