package src;

import src.BaseObjects.AstartesCategory;
import src.BaseObjects.MeleeWeapon;
import src.BaseObjects.Weapon;
import src.Command.Command;
import src.Command.ConcreteCommands.*;
import src.Command.Invoker;
import src.Command.Receiver;
import src.GUI.CommandButtons.NotNullStringsDialogWindow;
import src.GUI.GUI_AppOperations;
import src.Utils.ManagerOfCollection;

import javax.swing.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String [] astartesCategory = AstartesCategory.getList();
        String [] weapon = Weapon.getList();
        String [] meleeWeapon = MeleeWeapon.getList();

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
                "Weapon",
                JOptionPane.QUESTION_MESSAGE,
                null, meleeWeapon, meleeWeapon[0]).toString());

//        ManagerOfCollection.createMyCollection();
//        if (args[0].length() > 0) {
//            ManagerOfCollection.fillFromXml(args[0]);
//        }
//
//        Invoker commandInvoker = new Invoker();
//        HashMap<String, Command> invokerHashMap = commandInvoker.invokerHashMap;
//
//        Receiver commandReceiver = new Receiver(commandInvoker);
//
//         invokerHashMap.put("help", new Help(commandReceiver));
//         invokerHashMap.put("info", new Info(commandReceiver));
//         invokerHashMap.put("add", new Add(commandReceiver));
//         invokerHashMap.put("show", new Show(commandReceiver));
//         invokerHashMap.put("update", new Update(commandReceiver));
//         invokerHashMap.put("remove_by_id", new RemoveByID(commandReceiver));
//         invokerHashMap.put("clear", new Clear(commandReceiver));
//         invokerHashMap.put("exit", new Exit(commandReceiver));
//         invokerHashMap.put("remove_greater", new RemoveGreater(commandReceiver));
//         invokerHashMap.put("remove_lower", new RemoveLower(commandReceiver));
//         Чё с ней invokerHashMap.put("save", new Save(commandReceiver));
//         Делает ОЛЯ invokerHashMap.put("execute_script", new ExecuteScript(commandReceiver));
//         invokerHashMap.put("history", new History(commandReceiver));
//         invokerHashMap.put("remove_all_by_health", new RemoveAllByHealth(commandReceiver));
//         invokerHashMap.put("max_by_melee_weapon", new MaxByMeleeWeapon(commandReceiver));
//         invokerHashMap.put("print_unique_chapter", new PrintUniqueChapter(commandReceiver));
//
//        try(Scanner scanner = new Scanner(System.in)) {
//            while (scanner.hasNextLine()) {
//                commandInvoker.invoke(scanner.nextLine().trim().split("\s+"));
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
