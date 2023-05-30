package src.Utils.PasswordUtils.MyReaders;

import src.Utils.Makers.MyHashMaker;
import src.Utils.MyReaders.MyStringReader;

public class MyLoginReader
{
    public static String read()
    {
        String line = MyStringReader.read("Write login: ", false);
        MyHashMaker.setSalt(line);
        return line;
    }
}
