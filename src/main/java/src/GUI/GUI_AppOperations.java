package src.GUI;

import src.ClientServer.ClientStreams;
import src.GUI.CommandButtons.Help;
import src.User.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GUI_AppOperations
{
    private static Dimension mainSize;
    private static JFrame mainFrame;

    private static User user = ClientGUI.getUser();
    private static ClientStreams clientStreams = ClientGUI.getClientStreams();


    public static void App()
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

                JFrame frame = new JFrame("GUI Launcher");
                mainFrame = frame;

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setPreferredSize(size);

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

                JButton bHelp = new ImageTextButton("Help", scaledButtonImage, scaledPressedButtonImg, scaledDisabledButtonImg);;
                bHelp.setBounds(size.width / 2 - size.width / 10, 2 * size.height / 3, size.width / 5, size.height / 10);
                bHelp.setFont(new Font("Times New Roman", Font.BOLD, 25));
                bHelp.setForeground(Color.lightGray);

                Help help = null;

                bHelp.addActionListener(help);
                frame.getContentPane().add(bHelp);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
