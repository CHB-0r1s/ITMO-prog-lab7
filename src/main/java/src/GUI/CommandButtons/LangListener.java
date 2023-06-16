package src.GUI.CommandButtons;

import src.BaseObjects.MeleeWeapon;
import src.GUI.ClientGUI;
import src.GUI.GUI_AppOperations;
import src.GUI.Languages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LangListener implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String[] languages = Languages.getList();
        Languages lang = Enum.valueOf(Languages.class, JOptionPane.showInputDialog(
                GUI_AppOperations.getMainFrame(),
                null,
                "Language:",
                JOptionPane.QUESTION_MESSAGE,
                null, languages, languages[0]).toString());
        ClientGUI.getUser().setLanguage(lang.getLang());
    }
}
