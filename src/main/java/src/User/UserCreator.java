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
}
