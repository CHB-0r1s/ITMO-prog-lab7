package src.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI_Operations
{
    public static void startWindow()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        } catch (InstantiationException e)
        {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e)
        {
            throw new RuntimeException(e);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(screenSize);

        JLabel label = new JLabel();
        label = GUI_Image.setBGImage(label,"pics\\start-bg.jpg", screenSize);

        GUIConnectionToServer guiConnectionToServer = new GUIConnectionToServer();

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
        Image scaledButtonImage = buttonImg.getScaledInstance(screenSize.width/5, screenSize.height/10, Image.SCALE_REPLICATE);
        Image scaledPressedButtonImg = pressedButtonImg.getScaledInstance(screenSize.width/5, screenSize.height/10, Image.SCALE_REPLICATE);
        Image scaledDisabledButtonImg = disabledButtonImg.getScaledInstance(screenSize.width/5, screenSize.height/10, Image.SCALE_REPLICATE);

        JButton button = new ImageTextButton("Connect", scaledButtonImage, scaledPressedButtonImg, scaledDisabledButtonImg);

        button.setBounds(screenSize.width/2 - screenSize.width/10, 2*screenSize.height/3, screenSize.width/5, screenSize.height/10);
        button.setFont(new Font("Times New Roman",Font.BOLD,25));
        button.setForeground(Color.lightGray);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPressedIcon(new ImageIcon(scaledPressedButtonImg));

        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setIconTextGap(10);

        guiConnectionToServer.setButton(button);
        button.addActionListener(guiConnectionToServer);


        frame.getContentPane().add(button);
        frame.getContentPane().add(label);

        frame.pack();
        frame.setVisible(true);
    }

    //public static void
}
