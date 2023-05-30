package src.Utils.Makers;

import src.CredForPG;
import src.Utils.MyReaders.MyStringReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MyHashMaker
{
    private static String salt = "";
    public static String makeIn224 (String pswd) throws NoSuchAlgorithmException
    {
        setSalt("config.txt");
        pswd = pswd + salt;
        MessageDigest crypt = MessageDigest.getInstance("SHA-224");
        crypt.update(pswd.getBytes(StandardCharsets.UTF_8));

        byte[] bytes = crypt.digest();
        BigInteger bi = new BigInteger(1, bytes);
        String digest = String.format("%0" + (bytes.length << 1) + "x", bi);


        return digest;
    }

    public static void setSalt(String salt)
    {
        MyHashMaker.salt = salt;
    }

    private static void setSalt (File file)
    {
        try
        {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext())
            {
                String line = scanner.nextLine();
                if (line.contains("st"))
                {
                    line = line.replaceAll(".*: ", "");
                    salt = salt + line;
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("No config file");
        }
    }
}
