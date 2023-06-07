package src.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_SendUser implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(GUI_ConnectionToServer.isConnected())
        {
            GUI_LauncherOperations.registration();
        }
    }
}
