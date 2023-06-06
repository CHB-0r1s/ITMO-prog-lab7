package src.GUI;

import src.ClientServer.ClientStreams;
import src.User.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class ClientGUI
{
    private static JFrame mainFrame = new JFrame();
    private static Dimension mainSize = new Dimension();
    private static User user = new User(null,null);
    private static Socket clientSocket = new Socket();
    private static ClientStreams clientStreams = new ClientStreams();


    public static void setClientSocket(Socket clientSocket) throws IOException
    {
        ClientGUI.clientSocket = clientSocket;
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        ClientStreams clientStreamsGot = new ClientStreams();
        clientStreamsGot.setObjectOutputStream(objectOutputStream);
        clientStreamsGot.setBufferedReader(reader);
        clientStreams = clientStreamsGot;
    }

    public static Socket getClientSocket()
    {
        return clientSocket;
    }

    public static ClientStreams getClientStreams()
    {
        return clientStreams;
    }

    public static void setUser(User user)
    {
        ClientGUI.user = user;
    }

    public static User getUser()
    {
        return user;
    }

    public static Dimension getMainSize()
    {
        return mainSize;
    }

    public static JFrame getMainFrame()
    {
        return mainFrame;
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
                {
                    throw new RuntimeException(e);
                }

                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
                mainSize = size;

                JFrame frame = new JFrame("GUI");
                mainFrame = frame;

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setPreferredSize(size);

                GUI_Operations.startWindow(frame, size);

                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
