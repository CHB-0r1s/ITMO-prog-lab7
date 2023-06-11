package src.GUI.CommandButtons;

import src.ClientServer.ClientFunc;
import src.Command.*;
import src.GUI.ClientGUI;
import src.Utils.MyReaders.MyConfigReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

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
                String host = MyConfigReader.read("client-config.txt", "host");
                String port = MyConfigReader.read("client-config.txt", "port");

                try
                {
                    Socket clientSocket = new Socket(host, Integer.parseInt(port));
                    ClientGUI.setClientSocket(clientSocket);
                } catch (IOException ex)
                {
                    System.out.println("No connection.");
                }

                try
                {
                    ClientGUI.getUser().setNewable(false);
                    ClientFunc.sendUser(ClientGUI.getClientStreams().getObjectOutputStream(), ClientGUI.getUser());
                    if(ClientFunc.getResponseOfConnecting(ClientGUI.getClientStreams().getBufferedReader()))
                    {
                        System.out.println("Everything is ok!");
                        ClientGUI.getClientStreams().getObjectOutputStream().writeObject(command);
                        ClientGUI.getClientStreams().getObjectOutputStream().flush();
                    } else
                    {
                        System.out.println("Client did not accepted!");
                    }
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
