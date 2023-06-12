package src.GUI.CommandButtons;

import src.Command.ClientReceiver;
import src.Command.Command;
import src.GUI.GUI_AppOperations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExecuteScriptListener implements ActionListener
{
    private Command command;
    public ExecuteScriptListener(Command command)
    {
        this.command = command;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        while (true)
        {
            NotNullStringsDialogWindow fileWindow = new NotNullStringsDialogWindow
                    (GUI_AppOperations.getMainFrame(), "File name");
            String fileLine = fileWindow.getInputValue();

            try
            {
                command.setExtraDataFromClient(ClientReceiver.getCommandsFromScript(fileLine));
                break;
            } catch (IOException ex)
            {
                throw new RuntimeException(ex);
            }
        }
    }
}
