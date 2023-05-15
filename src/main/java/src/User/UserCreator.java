package src.User;

import src.Utils.PasswordUtils.LoginPasswordManager;
import src.Utils.PasswordUtils.MyReaders.MyLoginReader;
import src.Utils.PasswordUtils.MyReaders.MyPasswordReader;

import java.sql.SQLException;

public class UserCreator
{
    public static User createFromConsole(boolean newable) throws SQLException, ClassNotFoundException {
        User user = new User(MyLoginReader.read(), MyPasswordReader.read(), newable);
        LoginPasswordManager.writeToDataBase(user);
        return user;
    }

    public static void create(String login, String password)
    {
        //in another class, 'cause this class should be on client part??
    }
}
