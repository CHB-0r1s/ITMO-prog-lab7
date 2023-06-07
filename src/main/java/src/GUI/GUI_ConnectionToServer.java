package src.GUI;

import src.Utils.MyReaders.MyConfigReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class GUI_ConnectionToServer implements ActionListener
{
    private static JButton button = new JButton();
    static volatile boolean connected = false;

    public void setButton(JButton button)
    {
        GUI_ConnectionToServer.button = button;
    }

    public static boolean isConnected()
    {
        return connected;
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
                //button.setEnabled(false);
                ClientGUI.setClientSocket(clientSocket);
                connected = true;
                //here smt like signal -- check GUI_SendUser!!
            } catch (IOException ex)
            {
                System.out.println("No connection.");
            }
        }
    }
}
