package src.GUI;

import src.ClientServer.ClientFunc;
import src.User.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI_LauncherOperations
{
    public static void startWindow(JFrame frame, Dimension size)
    {


        JLabel label = new JLabel();
        label = GUI_Image.setBGImage(label, "pics\\start-bg.jpg", size);

        GUI_ConnectionToServer guiConnectionToServer = new GUI_ConnectionToServer();
        GUI_SendUser gui_sendUser = new GUI_SendUser();

        Image buttonImg;
        Image pressedButtonImg;
        Image disabledButtonImg;
        try
        {
            buttonImg = ImageIO.read(new File("pics\\button.png"));
            pressedButtonImg = ImageIO.read(new File("pics\\pressed_button.png"));
            disabledButtonImg = ImageIO.read(new File("pics\\disabled_button.png"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Image scaledButtonImage = buttonImg.getScaledInstance(size.width / 5, size.height / 10, Image.SCALE_REPLICATE);
        Image scaledPressedButtonImg = pressedButtonImg.getScaledInstance(size.width / 5, size.height / 10, Image.SCALE_REPLICATE);
        Image scaledDisabledButtonImg = disabledButtonImg.getScaledInstance(size.width / 5, size.height / 10, Image.SCALE_REPLICATE);

        JButton button = new LauncherButton("Connect", scaledButtonImage, scaledPressedButtonImg, scaledDisabledButtonImg);

        button.setBounds(size.width / 2 - size.width / 10, 2 * size.height / 3, size.width / 5, size.height / 10);
        button.setFont(new Font("Times New Roman", Font.BOLD, 25));
        button.setForeground(Color.lightGray);
        button.setPressedIcon(new ImageIcon(scaledPressedButtonImg));

        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);

        guiConnectionToServer.setButton(button);

        button.addActionListener(gui_sendUser);
        button.addActionListener(guiConnectionToServer);


        frame.getContentPane().add(button);
        frame.getContentPane().add(label);
    }

    private static void clearFrame (JFrame frame)
    {
        frame.getContentPane().removeAll();
    }

    public static void registration()
    {
        UIManager.put("OptionPane.okButtonText", "log in");
        UIManager.put("OptionPane.cancelButtonText", "sign up");
        Integer isLogging = JOptionPane.showOptionDialog(ClientGUI.getMainFrame(),
                "What to log in or sign up?", "Registration",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

        //"log" as "ok" is 0; "reg" as "cancel" is 2
        boolean isNewableUser = false;
        if(isLogging == 0)
        {
            isNewableUser = false;
        } else if(isLogging == 4)
        {
            isNewableUser = true;
        }
        isLogging = null;

        UIManager.put("OptionPane.okButtonText", "Ok");
        UIManager.put("OptionPane.cancelButtonText", "Cancel");

        String bufferLogin = JOptionPane.showInputDialog(ClientGUI.getMainFrame(),
                "login:", isLogging);
        String login = bufferLogin;
        bufferLogin = null;
        String pass = JOptionPane.showInputDialog(ClientGUI.getMainFrame(),
                "password:", bufferLogin);
        User user = new User(login, pass);
        user.setNewable(isNewableUser);

        ClientFunc.sendUser(ClientGUI.getClientStreams().getObjectOutputStream(), user);
        try
        {
            if(ClientFunc.getResponseOfConnecting(ClientGUI.getClientStreams().getBufferedReader()))
            {
                System.out.println("Everything is ok!");
                ClientGUI.setUser(user);
            } else
            {
                System.out.println("Client did not accepted!");
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
