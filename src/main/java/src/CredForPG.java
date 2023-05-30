package src;

public class CredForPG {
    protected static String login = "";
    protected static String pswd = "";

    public static String getNameForPG() {
        return login;
    }

    public static String getPswdForPG() {
        return pswd;
    }

    public static void setLogin(String login)
    {
        CredForPG.login = login;
    }

    public static void setPswd(String pswd)
    {
        CredForPG.pswd = pswd;
    }
}