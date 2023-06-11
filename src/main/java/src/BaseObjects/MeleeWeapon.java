package src.BaseObjects;

import java.io.Serializable;
import java.util.ArrayList;

public enum MeleeWeapon implements Serializable
{
    CHAIN_AXE,
    MANREAPER,
    LIGHTING_CLAW,
    POWER_BLADE,
    POWER_FIST;

    public static String[] getList()
    {
        ArrayList<String> list = new ArrayList<>();
        for(MeleeWeapon category:MeleeWeapon.values())
        {
            list.add(category.toString());
        }
        String[] array = list.toArray(new String[MeleeWeapon.values().length]);
        return array;
    }
}
