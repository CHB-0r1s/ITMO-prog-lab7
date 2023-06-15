package src.GUI;

import src.ClientServer.ClientStreams;
import src.Command.Command;
import src.Command.ConcreteCommands.*;
import src.GUI.CommandButtons.*;
import src.User.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GUI_AppOperations
{
    private static Dimension mainSize;
    private static JFrame mainFrame;

    public static JFrame getMainFrame()
    {
        return mainFrame;
    }

    private static User user = ClientGUI.getUser();
    private static ClientStreams clientStreams = ClientGUI.getClientStreams();


    public static void App()
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            throw new RuntimeException(e);
        }

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        mainSize = size;

        JFrame frame = new JFrame("GUI Launcher");
        mainFrame = frame;

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(size);

        Container container = frame.getContentPane();
        container.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton bHelp = createCommandButton(new Help());

        //commands with obj
        Add add = new Add();
        JButton bAdd = createCommandButton(add);
        ObjCreatorListener addListener = new ObjCreatorListener(add);
        bAdd.addActionListener(addListener);

        Update update = new Update();
        JButton bUpdate = createCommandButton(new Update());
        ObjCreatorListener updateListener = new ObjCreatorListener(update);
        bUpdate.addActionListener(updateListener);

        RemoveGreater removeGreater = new RemoveGreater();
        JButton bRemoveGreater = createCommandButton(new RemoveGreater());
        ObjCreatorListener removeGreaterListener = new ObjCreatorListener(removeGreater);
        bRemoveGreater.addActionListener(removeGreaterListener);

        RemoveLower removeLower = new RemoveLower();
        JButton bRemoveLower = createCommandButton(new RemoveLower());
        ObjCreatorListener removeLowerListener = new ObjCreatorListener(removeLower);
        bRemoveLower.addActionListener(removeLowerListener);

        //commands with simple extra data
        RemoveAllByHealth removeAllByHealth = new RemoveAllByHealth();
        JButton bRemoveAllByHeath = createCommandButton(removeAllByHealth);
        HealthListener healthListener = new HealthListener(removeAllByHealth);
        bRemoveAllByHeath.addActionListener(healthListener);

        RemoveByID removeByID = new RemoveByID();
        JButton bRemoveByID = createCommandButton(removeByID);
        IDListener idListener = new IDListener(removeByID);
        bRemoveByID.addActionListener(idListener);

        //execute script :(
        ExecuteScript executeScript = new ExecuteScript();
        JButton bExecuteScript = createCommandButton(executeScript);
        ExecuteScriptListener executeScriptListener = new ExecuteScriptListener(executeScript);
        bExecuteScript.addActionListener(executeScriptListener);

        //the simplest commands
        JButton bClear = createCommandButton(new Clear());
        JButton bInfo = createCommandButton(new Info());
        JButton bShow = createCommandButton(new Show());
        JButton bMaxByMeleeWeapon = createCommandButton(new MaxByMeleeWeapon());
        JButton bPrintUniqueChapter = createCommandButton(new PrintUniqueChapter());

        container.add(bHelp);
        container.add(bAdd);
        container.add(bExecuteScript);
        container.add(bClear);
        container.add(bInfo);
        container.add(bMaxByMeleeWeapon);
        container.add(bPrintUniqueChapter);
        container.add(bRemoveAllByHeath);
        container.add(bRemoveByID);
        container.add(bRemoveGreater);
        container.add(bRemoveLower);
        container.add(bShow);
        container.add(bUpdate);
        container.setPreferredSize(size);
        //frame.pack();
        frame.setVisible(true);
    }

    private static JButton createCommandButton (Command command)
    {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
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
        Image scaledButtonImage = buttonImg.getScaledInstance(size.width / 5, size.height / 10, Image.SCALE_REPLICATE);
        Image scaledPressedButtonImg = pressedButtonImg.getScaledInstance(size.width / 5, size.height / 10, Image.SCALE_REPLICATE);
        Image scaledDisabledButtonImg = disabledButtonImg.getScaledInstance(size.width / 5, size.height / 10, Image.SCALE_REPLICATE);

        JButton button = new ImageTextButton(command.getClass().getSimpleName(), scaledButtonImage, scaledPressedButtonImg, scaledDisabledButtonImg);;
        //button.setBounds(size.width / 2 - size.width / 10, 2 * size.height / 3, size.width / 5, size.height / 10);
        button.setPreferredSize(new Dimension(size.width / 5, size.height / 10));
        button.setFont(new Font("Times New Roman", Font.BOLD, 25));
        button.setForeground(Color.lightGray);

        CommandButtonListener commandListener = new CommandButtonListener(button, command);

        button.addActionListener(commandListener);


        return button;
    }
}