package src.Multithreading;

import src.ClientServer.ClientStreams;
import src.User.User;

import java.io.IOException;
import java.net.Socket;

public class Producer implements Runnable{
    private Socket socket;
    private User user;
    private ClientStreams clientStreams;

    public Producer(Socket socket, User user, ClientStreams clientStreams) {
        this.socket = socket;
        this.user = user;
        this.clientStreams = clientStreams;
    }

    @Override
    public void run() {
        while (true) {
            try {
                MyObjectReader reader = new MyObjectReader();
                reader.read(socket, user, clientStreams);
                Queueueue.add(reader);
                synchronized (Queueueue.queue) {
                    Queueueue.queue.notifyAll();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            catch (IllegalStateException Ie) {
                synchronized (Queueueue.queue) {
                    try {
                        Queueueue.queue.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
