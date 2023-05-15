package src.ClientServer;
// Да начнутся голодные игры!
import src.Command.Command;
import src.User.User;
import src.Utils.ManagerOfCollection;
import src.Utils.PasswordUtils.LoginPasswordManager;

import java.io.*;
import java.net.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLException;
import java.util.Scanner;

public class Server
{
    public static void main(String[] args) throws IOException
    {
        ManagerOfCollection.createMyCollection();
        LoginPasswordManager.fillMap();
        if (args[0].length() > 0) {
            ManagerOfCollection.fillFromXml(args[0]);
        }
        int port = MyPortReader.read("Write a port (in integer format, more than 1024):");

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true)
        {
            selector.select();
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client accepted");

            File fileName = new File("outServer.txt");

            try
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                PrintStream out = new PrintStream(new FileOutputStream(fileName));
                System.setOut(out);

                Scanner scanner = new Scanner(fileName);

                try
                {
                    boolean responseToClient = sendResponse(objectInputStream, writer);
                    if(responseToClient)
                    {
                        Command command = (Command) objectInputStream.readObject();
                        command.execute();
                        System.out.println(command);
                        out.close();
                        while (scanner.hasNextLine())
                        {
                            writer.write(scanner.nextLine() + "@");
                        }
                        writer.newLine();
                        writer.flush();
                    }
                } catch (ClassNotFoundException e)
                {
                    System.out.println(e);
                } catch (IllegalStateException | NullPointerException | SQLException ex)
                {
                    System.out.println("There is no command " + ". For reference, use – help");
                }
            }
            catch (SocketException e)
            {
                System.out.println("Client sent nothing and left.");
            }
        }
    }

    private static boolean sendResponse(ObjectInputStream objectInputStream, BufferedWriter writer) throws IOException, ClassNotFoundException
    {
        User user = (User) objectInputStream.readObject();
        boolean response = LoginPasswordManager.compareUser(user);

        writer.write(String.valueOf(response));
        writer.newLine();
        writer.flush();
        return response;
    }
}
