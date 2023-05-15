package src.Utils.PasswordUtils;

import src.User.User;
import src.User.UserCreator;

import java.util.HashMap;
import java.util.TreeMap;

public class LoginPasswordManager
{
    public static HashMap<String, String> users = new HashMap<>();

    public static void setUser(String login, String password)
    {
        users.put(login, password);
    }

    public static boolean compareUser(User user)
    {
        String password = LoginPasswordManager.users.get(user.getLogin());
        if (user.getPassword().equals(password))
        {
            return true;
        }
        if (user.getNewable())
        {
            setUser(user.getLogin(), user.getPassword());
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void fillMap()
    {
        //fill from db
        //UserCreator.create(login, password);
    }

    public static void writeToDataBase(User user)
    {
        //write to db
    }
}
