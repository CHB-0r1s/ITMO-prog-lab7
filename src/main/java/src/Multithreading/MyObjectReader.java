package src.Multithreading;

import src.ClientServer.ClientStreams;
import src.Command.Command;
import src.User.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class MyObjectReader
{
    private Command command;
    private User user;
    private Socket socket;

    public void read(Socket socket, User user, ClientStreams clientStreams) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = clientStreams.getObjectInputStream();
        Command command = (Command) objectInputStream.readObject();
        this.command = command;
        this.user = user;
        this.socket = socket;
    }

    public Command getCommand()
    {
        return command;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public User getUser()
    {
        return user;
    }
}
