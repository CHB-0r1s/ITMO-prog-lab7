package src.Utils.PasswordUtils.MyReaders;

import src.Utils.Makers.MyHashMaker;
import src.Utils.MyReaders.MyStringReader;

import java.security.NoSuchAlgorithmException;

public class MyPasswordReader
{
    public static String read()
    {
        try
        {
            return MyHashMaker.makeIn224(MyStringReader.read("Write password: ", false));
        } catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e);
        }
    }
}
