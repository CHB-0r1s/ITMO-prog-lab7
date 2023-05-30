package src.ClientServer;
import src.Command.Command;
import src.Multithreading.*;
import src.User.User;
import src.Utils.ManagerOfCollection;
import src.Utils.PasswordUtils.LoginPasswordManager;

import java.io.*;
import java.net.*;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

public class Server
{

    private static final ForkJoinPool pool = new ForkJoinPool();
    private static final int numberOfThreads = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        ManagerOfCollection.createMyCollection();
        ManagerOfCollection.fillFromPostgres();

        LoginPasswordManager.fillMap();

        int port = MyPortReader.read("Write a port (in integer format, more than 1024):");

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        Selector selector = Selector.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

//        MyConsumer consumer = new MyConsumer();
//        int numberOfThreads = Runtime.getRuntime().availableProcessors();
//        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
//        executorService.submit(consumer);

        while (true)
        {
            selector.select();
            Socket clientSocket = serverSocket.accept();

            try
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                try
                {
                    User user = (User) objectInputStream.readObject();
                    boolean responseToClient = ServerFunc.sendResponseOfConnecting(user, writer);

                    if(responseToClient)
                    {
//                        ForkJoinPool pool = new ForkJoinPool();
//                        pool.submit(new Producer(clientSocket));

                        Command command = (Command) objectInputStream.readObject();
                        String responseLine = ServerFunc.execution(command, user);
                        ServerFunc.writing(responseLine, clientSocket);
                    }
                } catch (IllegalStateException | NullPointerException ex)
                {
                    System.out.println("There is no command " + ". For reference, use – help");
                }
                catch (ClassNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
                objectInputStream.close();
            }
            catch (SocketException e)
            {
                System.out.println("Client sent nothing and left.");
            }
        }
    }
}
