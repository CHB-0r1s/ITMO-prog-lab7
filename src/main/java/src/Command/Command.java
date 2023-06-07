package src.Command;

import src.User.User;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.concurrent.ForkJoinTask;

public abstract class Command implements Serializable
{
    public abstract void execute(User user) throws IOException, SQLException, ClassNotFoundException;
    public abstract Command clientExecute();
    protected abstract String writeInfo();
    private Object extraDataFromClient;
//    private float floatFromClient;
//    private long longFromClient;
//    private double doubleFromClient;
//    private SpaceMarine spaceMarineFromClient;
//    private ArrayList<Command> commandsFromScript;

    public void setExtraDataFromClient(Object extraDataFromClient)
    {
        this.extraDataFromClient = extraDataFromClient;
    }

    public Object getExtraDataFromClient()
    {
        return extraDataFromClient;
    }
}
