package src.ClientServer;
// Да начнутся голодные игры!
import src.Command.Command;
import src.User.User;
import src.Utils.ManagerOfCollection;
import src.Utils.PasswordUtils.LoginPasswordManager;

import java.io.*;
import java.net.*;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Server
{
    private static boolean sendResponse(User user, BufferedWriter writer) throws IOException, ClassNotFoundException
    {
        boolean response = LoginPasswordManager.compareUser(user);

        writer.write(String.valueOf(response));
        writer.newLine();
        writer.flush();
        return response;
    }

    private static final ForkJoinPool pool = new ForkJoinPool();
    private static final int numberOfThreads = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService service = Executors.newFixedThreadPool(numberOfThreads/2);
    private static Socket clientSocket = new Socket();
    public static void setClientSocket(Socket socket)
    {
        clientSocket = socket;
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        ManagerOfCollection.createMyCollection();
        ManagerOfCollection.fillFromPostgres();

        LoginPasswordManager.fillMap();

        int port = MyPortReader.read("Write a port (in integer format, more than 1024):");

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = new ServerSocket(port);

        while (true)
        {
            //clientSocket = serverSocket.accept();
            pool.submit(ServerFunc.reading_connecting(serverSocket));

            File fileName = new File("outServer.txt");

            try
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                //^EOFException
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                PrintStream out = new PrintStream(new FileOutputStream(fileName));
                System.setOut(out);

                Scanner scanner = new Scanner(fileName);

                try
                {
                    User user = (User) objectInputStream.readObject();
                    boolean responseToClient = sendResponse(user, writer);
                    if(responseToClient)
                    {
                        Command command = (Command) objectInputStream.readObject();
                        command.execute(user);
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
                    System.out.println(e.getMessage());
                } catch (IllegalStateException | NullPointerException | SQLException ex)
                {
                    System.out.println("There is no command " + ". For reference, use – help");
                }
            }
            catch (SocketException e)
            {
                System.out.println("Client sent nothing and left.");
            }
            //pool.shutdown();
        }
    }
}
