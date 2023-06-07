package src.GUI.CommandButtons;

import src.ClientServer.ClientFunc;
import src.Command.*;
import src.GUI.ClientGUI;
import src.GUI.GUI_AppOperations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Help implements ActionListener
{
    private JButton button = new JButton();

    public Help(JButton button)
    {
        this.button = button;
    }

    public void setButton(JButton button)
    {
        this.button = button;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (button == e.getSource())
        {
            System.out.println("Im here");
            Command command = new src.Command.ConcreteCommands.Help(null, null);
            try
            {
                //ClientGUI.getUser().setNewable(false);
                //ClientFunc.sendUser(ClientGUI.getClientStreams().getObjectOutputStream(), ClientGUI.getUser());
                ClientGUI.getClientStreams().getObjectOutputStream().writeObject(command);
                ClientGUI.getClientStreams().getObjectOutputStream().flush();
            } catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }
}
