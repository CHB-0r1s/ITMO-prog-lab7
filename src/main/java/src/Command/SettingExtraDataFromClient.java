package src.Command;

import src.BaseObjects.SpaceMarine;
import src.Utils.MyReaders.MyFloatReader;
import src.Utils.MyReaders.MyLongReader;
import src.Utils.MyReaders.MyDoubleReader;
import src.Utils.SpaceMarineCreator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class SettingExtraDataFromClient
{
    public static void setFloatFromClient(Command command)
    {
        command.setExtraDataFromClient(MyFloatReader.read("Enter in float format: "));
    }
    public static void setLongFromClient(Command command)
    {
        command.setExtraDataFromClient(MyLongReader.read("Enter ID in long format: "));
    }

    public static void setLongFromClient(long longFromClient, Command command)
    {
        command.setExtraDataFromClient(longFromClient);
    }

    public static void setLongAndSpaceMarine(Command command)
    {
        ArrayList <Object> bufferListForIDObject = new ArrayList<>();
        bufferListForIDObject.add(MyLongReader.read("Enter ID in long format: "));
        bufferListForIDObject.add(SpaceMarineCreator.createSpaceMarine());
        command.setExtraDataFromClient(bufferListForIDObject);
    }

    public static void setDoubleFromClient(Command command)
    {
        command.setExtraDataFromClient(MyDoubleReader.read("Enter HP in double format: "));
    }

    public static void setDoubleFromClient(double doubleFromClient, Command command)
    {
        command.setExtraDataFromClient(doubleFromClient);
    }

    public static void setSpaceMarineFromClient(Command command)
    {
        SpaceMarine spaceMarine = SpaceMarineCreator.createSpaceMarine();
        spaceMarine.setId(null);
        command.setExtraDataFromClient(spaceMarine);
    }

    public static void setSpaceMarineFromClient(SpaceMarine spaceMarineFromClient, Command command)
    {
        spaceMarineFromClient.setId(null);
        command.setExtraDataFromClient(spaceMarineFromClient);
    }

    public static void setCommandsFromScript(String fileName, Command command) throws IOException {
        Invoker invoker = new Invoker();
        ClientReceiver clientReceiver = new ClientReceiver(invoker);
        command.setExtraDataFromClient(clientReceiver.getCommandsFromScript(fileName));
    }
}
