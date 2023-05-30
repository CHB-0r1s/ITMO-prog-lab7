package src.ClientServer;

import src.Command.Command;
import src.User.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class ServerFunc
{
    protected static Runnable reading_connecting (ServerSocket serverSocket)
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Server.setClientSocket(serverSocket.accept());
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    protected static Runnable execution (Command command, User user) throws SQLException, IOException, ClassNotFoundException
    {
        command.execute(user);
        return null;
    }

    protected static Runnable writing (Scanner scanner, BufferedWriter writer) throws IOException
    {
        while (scanner.hasNextLine())
        {
            writer.write(scanner.nextLine() + "@");
        }
        writer.newLine();
        writer.flush();
        return null;
    }
}
