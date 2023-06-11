package src.BaseObjects;

import java.io.Serializable;
import java.util.ArrayList;

public enum AstartesCategory implements Serializable
{
    SCOUT,
    DREADNOUGHT,
    AGGRESSOR;

    public static String[] getList()
    {
        ArrayList<String> list = new ArrayList<>();
        for(AstartesCategory category:AstartesCategory.values())
        {
            list.add(category.toString());
        }
        String[] array = list.toArray(new String[AstartesCategory.values().length]);
        return array;
    }
}
