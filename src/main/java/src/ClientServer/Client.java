package src.ClientServer;

import src.Command.Invoker;
import src.User.User;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
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
                    thisUser = ClientFunc.readUser(scanner);
                    ClientFunc.sendUser(objectOutputStream, thisUser);
                    firstTime = false;
                }
                else
                {
                    thisUser.setNewable(false);
                    ClientFunc.sendUser(objectOutputStream, thisUser);
                }
                if(ClientFunc.getResponseOfConnecting(reader))
                {
                    ClientFunc.sendCommand(commandInvoker, objectOutputStream, scanner);

                    ClientFunc.readResponse(clientSocket);

                    clientSocket.close();
                    Thread.sleep(1000);
                }
                else
                {
                    System.out.println("You are not accepted.");
                    firstTime = true;
                }

            } catch (IOException e)
            {
                System.out.println("Something went wrong...");
                System.exit(-1);
            } catch (InterruptedException | SQLException | ClassNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}