package src.GUI.CommandButtons;

import src.Command.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HealthListener implements ActionListener
{
    private Command command;
    public HealthListener(Command command)
    {
        this.command = command;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        Float health = ObjCreatorListener.getHealthFromGUI();
        command.setExtraDataFromClient(health);
    }
}
