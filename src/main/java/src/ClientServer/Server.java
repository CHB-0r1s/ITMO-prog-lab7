package src.ClientServer;
import src.Command.Command;
import src.Multithreading.MyConsumer;
import src.Multithreading.Producer;
import src.Multithreading.Queueueue;
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

        MyConsumer consumer = new MyConsumer();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        while (true)
        {
            selector.select();
            Socket clientSocket = serverSocket.accept();
            executorService.submit(consumer);
            System.err.println(Thread.currentThread());

            try
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                ClientStreams clientStreams = new ClientStreams(objectInputStream, writer);

                try
                {
                    User user = (User) objectInputStream.readObject();
                    boolean responseToClient = ServerFunc.sendResponseOfConnecting(user, clientStreams.getBufferedWriter());

                    if(responseToClient)
                    {
                        ForkJoinPool pool = new ForkJoinPool(1);
                        //Producer producer = new Producer(clientSocket, user, clientStreams);
                        pool.submit(new Producer(clientSocket, user, clientStreams));

//                        Command command = (Command) objectInputStream.readObject();
//                        String responseLine = ServerFunc.execution(command, user);
//                        ServerFunc.writing(responseLine, clientSocket);
                    }
                } catch (IllegalStateException | NullPointerException ex)
                {
                    System.out.println("There is no command " + ". For reference, use – help");
                }
                catch (ClassNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
            }
            catch (SocketException e)
            {
                System.out.println("Client sent nothing and left.");
            }
        }
    }
}
