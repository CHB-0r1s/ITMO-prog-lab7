package src.ClientServer;

import java.io.BufferedWriter;
import java.io.ObjectInputStream;

public class ClientStreams
{
    private ObjectInputStream objectInputStream;
    private BufferedWriter bufferedWriter;

    public ClientStreams(ObjectInputStream objectInputStream, BufferedWriter bufferedWriter)
    {
        this.objectInputStream = objectInputStream;
        this.bufferedWriter = bufferedWriter;
    }

    public BufferedWriter getBufferedWriter()
    {
        return bufferedWriter;
    }

    public ObjectInputStream getObjectInputStream()
    {
        return objectInputStream;
    }
}
