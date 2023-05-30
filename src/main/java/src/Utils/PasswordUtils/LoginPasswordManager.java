package src.Utils.PasswordUtils;

import src.BaseObjects.SpaceMarine;
import src.CredForPG;
import src.User.User;
import src.User.UserCreator;
import src.Utils.HeliosConnectable;
import src.Utils.ManagerOfCollection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class LoginPasswordManager implements HeliosConnectable
{
    public static HashMap<String, String> users = new HashMap<>();
    protected static boolean dbIsInit = false;

    public static void initDB() throws ClassNotFoundException, SQLException {
        Connection con = HeliosConnectable.createConToDB();
        Statement crt = con.createStatement();
        crt.executeUpdate("create table if not exists logpass" +
                "(login text, password text)");
        con.close();
        LoginPasswordManager.dbIsInit = true;
    }

    public static void insertUser(User user, Connection con) throws SQLException {
        PreparedStatement insSt = con.prepareStatement("insert into logpass " +
                "(login, password) " +
                "values (?, ?)");
        insSt.setString(1, user.getLogin());
        insSt.setString(2, user.getPassword());
        insSt.execute();
    }

    public static void setUser(String login, String password)
    {
        users.put(login, password);
        User user = new User(login, password);

        try
        {
            writeToDataBase(user);
        } catch (SQLException e)
        {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static boolean compareUser(User user)
    {
        String password = LoginPasswordManager.users.get(user.getLogin());
        if (user.getPassword().equals(password))
        {
            return true;
        }
        if (user.getNewable() && !users.keySet().contains(user.getLogin()))
        {
            setUser(user.getLogin(), user.getPassword());
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void fillMap() throws SQLException, ClassNotFoundException {
        if (!LoginPasswordManager.dbIsInit) {
            initDB();
        }
        try {
            Connection con = HeliosConnectable.createConToDB();
            Statement selSt = con.createStatement();
            ResultSet resultSet = selSt.executeQuery("select * from logpass");
            while (resultSet.next()) {
                String login = resultSet.getString(1);
                String password = resultSet.getString(2);
                users.put(login, password);
            }
            con.close();
        }
        catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeToDataBase(User user) throws SQLException, ClassNotFoundException {
        if (!LoginPasswordManager.dbIsInit) {
            initDB();
        }
        try {
            Connection con = HeliosConnectable.createConToDB();
            insertUser(user, con);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readFromFileForDB()
    {
        File fileConfig = new File("config.txt");
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(fileConfig);
        } catch (FileNotFoundException e)
        {
            System.out.println("No config file");
        }
        while (scanner.hasNext())
        {
            String line = scanner.nextLine();
            if (line.contains("fnlp"))
            {
                File file = new File(line.replaceAll(".*: ", ""));
                try
                {
                    Scanner scannerForLP = new Scanner(file);
                    String pseudoLogPas = scannerForLP.nextLine();
                    String [] logPas = pseudoLogPas.split(":");
                    CredForPG.setLogin(logPas[3]);
                    CredForPG.setPswd(logPas[4]);
                }
                catch (FileNotFoundException e)
                {
                    System.out.println("Cannot find file from config.");
                }
            }
        }
    }
}
