package src.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class ClientGUI
{
    public static void main(String[] args)
    {
        JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createGUI();
            }
        });
    }


    public static void createGUI()
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

        BufferedImage wPic = null;
        try
        {
            wPic = ImageIO.read(new File("D:\\картинки)\\captain-titus-warhammer-40000-space-marine-4k-new.jpg"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        JFrame frame = new JFrame("GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello world");
        frame.getContentPane().add(label);

        JLabel pic = new JLabel(new ImageIcon(wPic));
        frame.getContentPane().add(pic);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setPreferredSize(dimension);

        frame.pack();
        frame.setVisible(true);
    }
}
