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
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;
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
    private static final ExecutorService service = Executors.newFixedThreadPool(numberOfThreads);
    private static final ExecutorService cashedService = Executors.newCachedThreadPool();
    private static Socket clientSocket = new Socket();
    public static void setClientSocket(Socket socket) throws IOException
    {
        clientSocket = socket;
//        SocketChannel channel = getClientChannel(socket);
//        channel.configureBlocking(false);
//        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        //переписать input/output stream с учётом селектора
    }

    private static SocketChannel getClientChannel(Socket socket)
    {
        return socket.getChannel();
    }

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

        while (true)
        {
            //clientSocket = serverSocket.accept();
            pool.submit(ServerFunc.reading(serverSocket, selector));

            //System.out.println(pool.getPoolSize());

            File file = new File("outServer.txt");

            try
            {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                Scanner scanner = new Scanner(file);

                try
                {
                    User user = (User) objectInputStream.readObject();
                    boolean responseToClient = sendResponse(user, writer);
                    if(responseToClient)
                    {
                        Command command = (Command) objectInputStream.readObject();
                        //assert ServerFunc.execution(command, user) == null;
                        service.execute(ServerFunc.execution(command, user, file));
                        System.out.println(command);

                        service.execute(ServerFunc.writing(scanner, writer));

//                        ServerFunc.getLock().lock();
//                        ServerFunc.getConditionForDisableClient().await();
//                        ServerFunc.getLock().unlock();
                        while (!ServerFunc.anotherCheck)
                        {

                        }
                        ServerFunc.anotherCheck = false;

//                        while (scanner.hasNextLine())
//                        {
//                            writer.write(scanner.nextLine() + "@");
//                        }
//                        writer.newLine();
//                        writer.flush();
                    }
                    Thread.sleep(100);
                } catch (ClassNotFoundException e)
                {
                    System.out.println(e.getMessage());
                } catch (IllegalStateException | NullPointerException | SQLException ex)
                {
                    System.out.println("There is no command " + ". For reference, use – help");
                }
                catch (InterruptedException e)
                {
                    throw new RuntimeException(e);
                }
                objectInputStream.close();
            }
            catch (SocketException e)
            {
                //System.out.println("Client sent nothing and left.");
            }
            //pool.shutdown();
        }
    }
}
