package src.Utils.PasswordUtils.MyReaders;

import src.Utils.MyReaders.MyStringReader;

public class MyLoginReader
{
    public static String read()
    {
        return MyStringReader.read("Write login: ", false);
    }
}
