package src.ClientServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientStreams
{
    private ObjectInputStream objectInputStream;
    private BufferedWriter bufferedWriter;
    private ObjectOutputStream objectOutputStream;
    private BufferedReader bufferedReader;

    public ClientStreams(ObjectInputStream objectInputStream, BufferedWriter bufferedWriter)
    {
        this.objectInputStream = objectInputStream;
        this.bufferedWriter = bufferedWriter;
    }

    public void setBufferedReader(BufferedReader bufferedReader)
    {
        this.bufferedReader = bufferedReader;
    }

    public BufferedReader getBufferedReader()
    {
        return bufferedReader;
    }

    public void setObjectOutputStream(ObjectOutputStream objectOutputStream)
    {
        this.objectOutputStream = objectOutputStream;
    }

    public ClientStreams()
    {

    }

    public BufferedWriter getBufferedWriter()
    {
        return bufferedWriter;
    }

    public ObjectInputStream getObjectInputStream()
    {
        return objectInputStream;
    }

    public ObjectOutputStream getObjectOutputStream()
    {
        return objectOutputStream;
    }
}
