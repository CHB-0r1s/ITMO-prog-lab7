package src.GUI;

import javax.swing.*;
import java.awt.*;

public class ImageTextButton extends JButton
{
    private Image backgroundImage;
    private String buttonText;

    private Image backgroundPressedImage;

    public ImageTextButton(String buttonText, Image backgroundImage, Image backgroundPressedImage)
    {
        this.buttonText = buttonText;
        this.backgroundImage = backgroundImage;
        this.backgroundPressedImage = backgroundPressedImage;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        if (backgroundImage != null)
        {
            graphics.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (buttonText != null)
        {
            FontMetrics metrics = graphics.getFontMetrics(getFont());
            int x = (getWidth() - metrics.stringWidth(buttonText)) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            graphics.setColor(getForeground());
            graphics.drawString(buttonText, x, y);
        }

        if (getModel().isPressed())
        {
            if (backgroundPressedImage != null)
            {
                graphics.drawImage(backgroundPressedImage, 0, 0, getWidth(), getHeight(), this);
            }

            if (buttonText != null)
            {
                FontMetrics metrics = graphics.getFontMetrics(getFont());
                int x = (getWidth() - metrics.stringWidth(buttonText)) / 2;
                int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
                graphics.setColor(getForeground());
                graphics.drawString(buttonText, x, y);
            }
        }
    }
}
