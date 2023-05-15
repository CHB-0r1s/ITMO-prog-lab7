package src.Utils.Makers;

import src.Utils.MyReaders.MyStringReader;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyHashMaker
{
    public static String makeIn224 (String line) throws NoSuchAlgorithmException
    {
        MessageDigest crypt = MessageDigest.getInstance("SHA-224");
        crypt.update(line.getBytes(StandardCharsets.UTF_8));

        byte[] bytes = crypt.digest();
        BigInteger bi = new BigInteger(1, bytes);
        String digest = String.format("%0" + (bytes.length << 1) + "x", bi);


        return digest;
    }
}
