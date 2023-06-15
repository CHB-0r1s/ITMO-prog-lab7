package src.Command;

import src.User.User;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public abstract class Command implements Serializable
{
    public abstract void execute(User user) throws IOException, SQLException, ClassNotFoundException;
    public abstract Command clientExecute() throws IOException;
    protected abstract String writeInfo();

    protected Receiver commandReceiver;
    protected ClientReceiver clientReceiver;
    private Object extraDataFromClient;
    protected boolean tableTypeCommand = false;
    public boolean getTableType()
    {
        return tableTypeCommand;
    }
    public void setExtraDataFromClient(Object extraDataFromClient)
    {
        this.extraDataFromClient = extraDataFromClient;
    }

    public Object getExtraDataFromClient()
    {
        return extraDataFromClient;
    }

    public Command()
    {
    }

    public void setCommandReceiver(Receiver commandReceiver)
    {
        this.commandReceiver = commandReceiver;
    }

    public void setClientReceiver(ClientReceiver clientReceiver)
    {
        this.clientReceiver = clientReceiver;
    }
}
