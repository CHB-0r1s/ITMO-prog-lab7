package src.User;

import java.io.Serializable;

public class User implements Serializable
{
    private String login;
    private String password;
    private boolean newable;

    User (String login, String password, boolean newable)
    {
        this.login = login;
        this.password = password;
        this.newable = newable;
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
}
