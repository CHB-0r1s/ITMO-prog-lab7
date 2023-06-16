package src.Multithreading;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Response implements Runnable, Serializable {
    transient Socket socket;
    String message;

    public String getMessage()
    {
        return new String(message.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setSocket(Socket socket)
    {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
