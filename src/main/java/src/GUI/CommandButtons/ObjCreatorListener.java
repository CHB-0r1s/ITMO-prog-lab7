package src.GUI.CommandButtons;

import src.BaseObjects.*;
import src.BaseObjects.MeleeWeapon;
import src.BaseObjects.Weapon;
import src.Command.Command;
import src.GUI.GUI_AppOperations;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObjCreatorListener implements ActionListener
{
    private Command command;

    public ObjCreatorListener(Command command) {this.command = command;}

    @Override
    public void actionPerformed(ActionEvent e)
    {
        String [] astartesCategory = AstartesCategory.getList();
        String [] weapon = Weapon.getList();
        String [] meleeWeapon = MeleeWeapon.getList();


        NotNullStringsDialogWindow nameWindow = new NotNullStringsDialogWindow
                (GUI_AppOperations.getMainFrame(), "Name");
        NotNullStringsDialogWindow chapterNameWindow = new NotNullStringsDialogWindow
                (GUI_AppOperations.getMainFrame(), "Chapter Name");
        NotNullStringsDialogWindow parentLegionWindow = new NotNullStringsDialogWindow
                (GUI_AppOperations.getMainFrame(), "Parent Legion");

        String nameObj = nameWindow.getInputValue();
        String chapterNameObj = chapterNameWindow.getInputValue();
        String parentLegion = parentLegionWindow.getInputValue();

        Float xObj;
        Double yObj;
        Float healthObj;
        while (true)
        {
            try
            {
                NotNullStringsDialogWindow xWindow = new NotNullStringsDialogWindow
                        (GUI_AppOperations.getMainFrame(), "X (coordinates)");
                xObj = Float.parseFloat(xWindow.getInputValue());
                break;
            } catch (NumberFormatException ex) {}
        }
        while (true)
        {
            try
            {
                NotNullStringsDialogWindow yWindow = new NotNullStringsDialogWindow
                        (GUI_AppOperations.getMainFrame(), "Y (coordinates)");
                yObj = Double.parseDouble(yWindow.getInputValue());
                break;
            } catch (NumberFormatException ex) {}
        }
        healthObj = getHealthFromGUI();

        AstartesCategory astartesCategoryObj = Enum.valueOf(AstartesCategory.class, JOptionPane.showInputDialog(
                GUI_AppOperations.getMainFrame(),
                null,
                "AstartesCategory",
                JOptionPane.QUESTION_MESSAGE,
                null, astartesCategory, astartesCategory[0]).toString());
        Weapon weaponObj = Enum.valueOf(Weapon.class, JOptionPane.showInputDialog(
                GUI_AppOperations.getMainFrame(),
                null,
                "Weapon",
                JOptionPane.QUESTION_MESSAGE,
                null, weapon, weapon[0]).toString());
        MeleeWeapon meleeWeaponObj = Enum.valueOf(MeleeWeapon.class, JOptionPane.showInputDialog(
                GUI_AppOperations.getMainFrame(),
                null,
                "Melee Weapon",
                JOptionPane.QUESTION_MESSAGE,
                null, meleeWeapon, meleeWeapon[0]).toString());

        Coordinates coordinates = new Coordinates(xObj, yObj);
        Chapter chapter = new Chapter(chapterNameObj, parentLegion);

        SpaceMarine spaceMarine = new SpaceMarine(nameObj, coordinates,
                healthObj, astartesCategoryObj,
                weaponObj, meleeWeaponObj, chapter);

        command.setExtraDataFromClient(spaceMarine);
    }

    public static Float getHealthFromGUI()
    {
        while (true)
        {
            try
            {
                NotNullStringsDialogWindow healthWindow = new NotNullStringsDialogWindow
                        (GUI_AppOperations.getMainFrame(), "Health");
                Float healthObj = Float.parseFloat(healthWindow.getInputValue());
                return healthObj;
            } catch (NumberFormatException ex) {}
        }
    }

    public static Long getIDFromGUI()
    {
        while (true)
        {
            try
            {
                NotNullStringsDialogWindow healthWindow = new NotNullStringsDialogWindow
                        (GUI_AppOperations.getMainFrame(), "Health");
                Long ID = Long.parseLong(healthWindow.getInputValue());
                return ID;
            } catch (NumberFormatException ex) {}
        }
    }
}
