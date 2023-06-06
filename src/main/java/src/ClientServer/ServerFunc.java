package src.ClientServer;

import src.Command.Command;
import src.Multithreading.Response;
import src.User.User;
import src.Utils.PasswordUtils.LoginPasswordManager;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ServerFunc
{
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    protected static boolean sendResponseOfConnecting(User user, BufferedWriter writer) throws IOException, ClassNotFoundException
    {
        boolean response = LoginPasswordManager.compareUser(user);

        writer.write(String.valueOf(response));
        writer.newLine();
        writer.flush();
        return response;
    }

    public static String execution (Command command, User user)
    {
        try
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream out = new PrintStream(outputStream);
            System.setOut(out);
            System.err.println(command);
            command.execute(user);
            out.close();
            String response = outputStream.toString();
            return response;
        } catch (IOException | SQLException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    protected static void writing (String line, Socket socket)
    {
        Response response = new Response();
        response.setMessage(line);
        response.setSocket(socket);

        response.run();
    }
}
