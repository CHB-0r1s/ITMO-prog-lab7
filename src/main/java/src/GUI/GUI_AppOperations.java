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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        container.setLayout(flowLayout);
        container.setBackground(new Color(43, 35, 35));


        Image beautiful;
        try
        {
            beautiful = ImageIO.read(new File("pics\\bottom_pic.png"));
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        Image scaledBeauPic = beautiful.getScaledInstance(size.width, size.height / 30, Image.SCALE_REPLICATE);
        JLabel pic_label = new JLabel(new ImageIcon(scaledBeauPic));


        JScrollPane objects = createScrolledText(new Dimension(size.width, size.height/(2)), new Show());
        container.add(objects);
        container.add(pic_label);
        JScrollPane others = createScrolledText(new Dimension(size.width, size.height/(4)), nullCmd);
        container.add(others);




        //__________________commands___________________

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
        JButton bHelp = createCommandButton(new Help());

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

        //__________________commands___________________closed

        frame.setVisible(true);
    }
    private static final Command nullCmd = new Command()
    {
        @Override
        public void execute(User user) throws IOException, SQLException, ClassNotFoundException
        {

        }

        @Override
        public Command clientExecute() throws IOException
        {
            return null;
        }

        @Override
        protected String writeInfo()
        {
            return null;
        }
    };

    private static HashMap<Class,JLabel> repainters = new HashMap<>();

    public static void thingsForRepaint(Command command)
    {
        boolean check = true;
        for(Class cl:repainters.keySet())
        {
            if(command.getClass().equals(cl))
            {
                repainters.get(command.getClass()).setText(ResponseToGUI.getHtml());
                repainters.get(command.getClass()).repaint();
                check = false;
            }
        }
        if(check)
        {
            for(Class cl:repainters.keySet())
            {
                if(cl.equals(nullCmd.getClass()))
                {
                    repainters.get(nullCmd.getClass()).setText(ResponseToGUI.getHtml());
                    repainters.get(nullCmd.getClass()).repaint();
                    check = false;
                }
            }
        }
    }

    private static JScrollPane createScrolledText(Dimension dimension, Command command)
    {
        JLabel label = new JLabel(ResponseToGUI.getHtml(), SwingConstants.CENTER);
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Times New Roman", Font.BOLD, 14));
        repainters.put(command.getClass(), label);
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(dimension);
        return scrollPane;
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
        button.setPreferredSize(new Dimension(size.width / 10, size.height / 20));
        button.setFont(new Font("Times New Roman", Font.BOLD, 15));
        button.setForeground(Color.lightGray);

        CommandButtonListener commandListener = new CommandButtonListener(button, command);

        button.addActionListener(commandListener);


        return button;
    }
}
