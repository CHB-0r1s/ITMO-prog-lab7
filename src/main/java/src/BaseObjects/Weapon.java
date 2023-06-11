package src.BaseObjects;

import java.io.Serializable;
import java.util.ArrayList;

public enum Weapon implements Serializable
{
    HEAVY_BOLTGUN,
    BOLT_PISTOL,
    FLAMER,
    HEAVY_FLAMER,
    NULL;

    public static String[] getList()
    {
        ArrayList<String> list = new ArrayList<>();
        for(Weapon category:Weapon.values())
        {
            list.add(category.toString());
        }
        String[] array = list.toArray(new String[Weapon.values().length]);
        return array;
    }
}
