package src.ClientServer;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MyPortReader
{
    static int read(String consoleMessage)
    {
        Scanner reader = new Scanner(System.in);
        System.out.println(consoleMessage);
        while (true)
        {
            String line = reader.nextLine();
            try
            {
                int number = Integer.parseInt(line);
                if (number <= 1024)
                {
                    System.out.println("Port must be more than 1024.");
                } else
                {
                    return number;
                }
            } catch (NumberFormatException e)
            {
                System.out.println("Not right input. Try again.");
            }
        }
    }
}
