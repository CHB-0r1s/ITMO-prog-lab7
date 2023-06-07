package src.ClientServer;

import src.Command.Command;
import src.Command.Invoker;
import src.Multithreading.Response;
import src.User.User;
import src.User.UserCreator;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;

public class ClientFunc
{
    protected static void sendCommand(Invoker commandInvoker, ObjectOutputStream objectOutputStream, Scanner scanner)
    {
        try {
            while (scanner.hasNextLine()) {
                Command command = commandInvoker.invokeForClient(scanner.nextLine().trim().split("\s+"));
                if (command != null)
                {
                    objectOutputStream.writeObject(command);
                    objectOutputStream.flush();
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void sendUser(ObjectOutputStream objectOutputStream, User user)
    {
        try
        {
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    protected static User readUser(Scanner scanner) throws SQLException, ClassNotFoundException {
        User user;
        System.out.println("Do you want to log in or sign up?");
        while (true)
        {
            String logOrSign = scanner.nextLine();
            if (logOrSign != null && (logOrSign.toLowerCase().contains("log") || logOrSign.toLowerCase().contains("sign")))
            {
                if (logOrSign.toLowerCase().contains("log"))
                {
                    user = UserCreator.createFromConsole(false);
                    break;
                }
                if (logOrSign.toLowerCase().contains("sign"))
                {
                    user = UserCreator.createFromConsole(true);
                    break;
                }
            }
            else
            {
                System.out.println("Incorrect input. Try again.");
            }
        }
        return user;
    }

    public static boolean getResponseOfConnecting(BufferedReader reader) throws IOException
    {
        return Boolean.parseBoolean(reader.readLine());
    }

    protected static void readResponse(Socket clientSocket) throws IOException, ClassNotFoundException
    {
        ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        Response response = (Response) objectInputStream.readObject();
        System.out.println(response.getMessage());
    }
}
