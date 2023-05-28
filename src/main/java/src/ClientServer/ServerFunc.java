package src.ClientServer;

import src.Command.Command;
import src.User.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.Selector;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ServerFunc
{
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static Condition getCondition()
    {
        return condition;
    }

    private static Condition conditionForDisableClient = lock.newCondition();

    public static Condition getConditionForDisableClient()
    {
        return conditionForDisableClient;
    }

    private static boolean check = false;
    public static boolean anotherCheck = false;

    public static Lock getLock()
    {
        return lock;
    }

    protected static Runnable reading (ServerSocket serverSocket, Selector selector)
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    selector.select();
                    Server.setClientSocket(serverSocket.accept());
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    protected static Runnable execution (Command command, User user, File fileName) throws SQLException, IOException, ClassNotFoundException
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    lock.lock();
                    PrintStream out = new PrintStream(new FileOutputStream(fileName));
                    System.setOut(out);
                    command.execute(user);
                    out.close();
                    condition.signal();
                    check = true;
                    lock.unlock();
                } catch (IOException | SQLException | ClassNotFoundException e)
                {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    protected static Runnable writing (Scanner scanner, BufferedWriter writer) throws InterruptedException
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                lock.lock();
//                try
//                {
//                    condition.await();
//                    System.out.println("Using");
//                } catch (InterruptedException e)
//                {
//                    throw new RuntimeException(e);
//                }
                while (!check)
                {

                }
                check = false;
                while (scanner.hasNextLine())
                {
                    try
                    {
//                        synchronized (writer)
//                        {
                            writer.write(scanner.nextLine() + "@");
                        //}
                    } catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
                try
                {
                    writer.newLine();
                    writer.flush();
                } catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                conditionForDisableClient.signal();
                anotherCheck = true;
                lock.unlock();
            }
        };
    }
}
