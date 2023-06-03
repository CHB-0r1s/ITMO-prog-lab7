package src.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI_Image
{
    public static JLabel setBGImage(JLabel label, String imageFile, Dimension size)
    {
        int width = size.width;
        int height = size.height;

        label.setPreferredSize(new Dimension(width, height));

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);

        label.setIcon(new ImageIcon(scaledImage));
        return label;
    }
}
