package src.ClientServer;

import src.Command.Command;
import src.Command.Invoker;
import src.User.User;
import src.User.UserCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args)
    {
        String host = MyHostReader.read("Write a host (in format IPv4):");
        int port = MyPortReader.read("Write a port (in integer format, more than 1024):");
        Scanner scanner = new Scanner(System.in);
        boolean firstTime = true;
        User thisUser = null;

        while (true)
        {
            try
            {
                Socket clientSocket = new Socket(host, port);

                Invoker commandInvoker = new Invoker();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

                if(firstTime)
                {
                    thisUser = sendingUser(objectOutputStream, scanner);
                    firstTime = false;
                }
                else
                {
                    thisUser.setNewable(false);
                    sendingUser(objectOutputStream, thisUser);
                }
                if(getResponse(reader))
                {
                    sendingCommand(commandInvoker, objectOutputStream, scanner);

                    String getMsg = reader.readLine().replaceAll("@", "\n");
                    System.out.println(getMsg);
                    clientSocket.close();
                    Thread.sleep(1000);
                }
                else
                {
                    System.out.println("You are not accepted.");
                }

            } catch (IOException e)
            {
                System.out.println("Something went wrong...");
                System.exit(-1);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    private static void sendingCommand(Invoker commandInvoker, ObjectOutputStream objectOutputStream, Scanner scanner)
    {
        try{
            while (scanner.hasNextLine()) {
                Command command = commandInvoker.invokeForClient(scanner.nextLine().trim().split("\s+"));
                if (command != null)
                {
                    objectOutputStream.writeObject(command);
                    objectOutputStream.flush();
                    return;
                }
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static Socket getConnection(String host, int port) throws IOException
    {
        while (true)
        {
            try
            {
                return new Socket(host, port);
            } catch (UnknownHostException | ConnectException e)
            {
                System.out.println("No connection.");
            }
        }
    }

    private static void sendingUser(ObjectOutputStream objectOutputStream, User user)
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



    private static User sendingUser(ObjectOutputStream objectOutputStream, Scanner scanner)
    {
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
        try
        {
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            return user;
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static boolean getResponse(BufferedReader reader) throws IOException
    {
        boolean response = Boolean.valueOf(reader.readLine());
        return response;
    }
}