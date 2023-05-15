package src.ClientServer;

import java.util.Scanner;
import java.util.regex.*;

public class MyHostReader
{
    static String read(String consoleMessage)
    {
        Scanner reader = new Scanner(System.in);
        Pattern pattern = Pattern.compile("^((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.){3}(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])$");
        System.out.println(consoleMessage);
        while (true)
        {
            String line = reader.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches())
            {
                return line;
            } else
            {
                System.out.println("Wrong format. Try again.");
            }
        }
    }
}
