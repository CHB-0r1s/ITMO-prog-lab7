package src.GUI;

import src.BaseObjects.AstartesCategory;

import java.util.ArrayList;

public enum Languages
{
    ENG ("Eng"),
    RUS ("Rus"),
    VENG ("Ven"),
    MAK ("Mak");
    private final String lang;
    private Languages(String lang)
    {
        this.lang = lang;
    }

    public String getLang()
    {
        return lang;
    }

    public static String[] getList()
    {
        ArrayList<String> list = new ArrayList<>();
        for(Languages category:Languages.values())
        {
            list.add(category.toString());
        }
        String[] array = list.toArray(new String[Languages.values().length]);
        return array;
    }
}
