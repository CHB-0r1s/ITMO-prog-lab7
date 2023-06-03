package src.Utils.MyReaders;

import src.CredForPG;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyConfigReader
{
    public static String read (String fileName, String item)
    {
        File fileConfig = new File(fileName);
        Scanner scanner = null;
        try
        {
            scanner = new Scanner(fileConfig);
        } catch (FileNotFoundException e)
        {
            System.out.println("No config file");
        }
        while (scanner.hasNext())
        {
            String line = scanner.nextLine();
            if (line.contains(item))
            {
                line = line.replaceAll(".*: ", "");
                return line;
            }
        }
        return null;
    }
}
