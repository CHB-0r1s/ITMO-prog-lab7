package src.GUI.CommandButtons;

import src.ClientServer.ClientFunc;
import src.Command.*;
import src.GUI.ClientGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CommandButtonListener implements ActionListener
{
    private JButton button = new JButton();
    private Command command;
    private static boolean firstTime = true;

    public CommandButtonListener(JButton button, Command command)
    {
        this.button = button;
        this.command = command;
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
            Command command = this.command;
            if(firstTime)
            {
                try
                {
                    ClientGUI.getClientStreams().getObjectOutputStream().writeObject(command);
                    ClientGUI.getClientStreams().getObjectOutputStream().flush();
                    firstTime = false;
                } catch (IOException ex)
                {
                    throw new RuntimeException(ex);
                }
            }
            else
            {
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

        try
        {
            ClientFunc.readResponse(ClientGUI.getClientSocket());
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex)
        {
            throw new RuntimeException(ex);
        }
    }
}
