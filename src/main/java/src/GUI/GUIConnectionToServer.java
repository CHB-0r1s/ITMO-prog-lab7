package src.GUI;

import src.Utils.MyReaders.MyConfigReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class GUIConnectionToServer implements ActionListener
{
    private static JButton button = new JButton();

    public void setButton(JButton button)
    {
        GUIConnectionToServer.button = button;
    }

    public static JButton getButton()
    {
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (button == e.getSource())
        {
            String host = MyConfigReader.read("client-config.txt", "host");
            String port = MyConfigReader.read("client-config.txt", "port");

            try
            {
                Socket clientSocket = new Socket(host, Integer.parseInt(port));
            } catch (IOException ex)
            {
                System.out.println("No connection.");
            }
        }
    }
}
