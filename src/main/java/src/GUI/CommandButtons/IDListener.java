package src.GUI.CommandButtons;

import src.Command.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IDListener implements ActionListener
{
    private Command command;
    public IDListener(Command command)
    {
        this.command = command;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Long ID = ObjCreatorListener.getIDFromGUI();
        command.setExtraDataFromClient(ID);
    }
}
