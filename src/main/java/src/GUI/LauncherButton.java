package src.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.locks.ReentrantLock;

public class LauncherButton extends ImageTextButton
{
    private static boolean firstTime = false;
    private Image backgroundImage;
    private String buttonText;

    private Image backgroundPressedImage;
    private Image backgroundDisabledImage;
    private ReentrantLock lock = new ReentrantLock();

    public LauncherButton(String buttonText, Image backgroundImage, Image backgroundPressedImage, Image backgroundDisabledImage)
    {
        super(buttonText, backgroundImage, backgroundPressedImage, backgroundDisabledImage);
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        lock.lock();
        if (ClientGUI.getUser() != null && firstTime == false)
        {
            setEnabled(false);
            GUI_AppOperations.App();
            firstTime = true;
        }
        lock.lock();

        ImageTextButton button = new ImageTextButton(buttonText, backgroundImage, backgroundPressedImage, backgroundDisabledImage);
        button.paintComponent(graphics);
    }
}
