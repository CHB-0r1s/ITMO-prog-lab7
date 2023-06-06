package src.GUI.CommandButtons;

import src.ClientServer.ClientFunc;
import src.Command.Command;
import src.GUI.ClientGUI;
import src.GUI.GUI_AppOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Help implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println("Im here");
        Help command = null;
        try
        {
            ClientGUI.getUser().setNewable(false);
            ClientFunc.sendUser(ClientGUI.getClientStreams().getObjectOutputStream(), ClientGUI.getUser());
            ClientGUI.getClientStreams().getObjectOutputStream().writeObject(command);
            ClientGUI.getClientStreams().getObjectOutputStream().flush();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
