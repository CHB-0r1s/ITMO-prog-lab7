package src.User;

import src.Utils.Makers.MyHashMaker;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable
{
    private String login;
    private String password;
    private String language;
    private boolean newable;

    public User (String login, String password, boolean newable)
    {
        this.login = login;
        this.password = password;
        this.newable = newable;
    }

    public User (String login, String password)
    {
        this.login = login;
        try
        {
            this.password = MyHashMaker.makeIn224(password);
        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }

    void setLogin(String login)
    {
        this.login = login;
    }

    void setPassword(String password)
    {
        this.password = password;
    }

    public void setNewable(boolean newable)
    {
        this.newable = newable;
    }

    public String getLogin()
    {
        return login;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean getNewable()
    {
        return newable;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
