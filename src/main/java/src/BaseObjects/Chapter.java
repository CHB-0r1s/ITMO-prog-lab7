package src.BaseObjects;

import java.io.Serializable;
import java.util.Objects;

public class Chapter implements Comparable<Chapter>, Serializable
{
    private String name;
    private String parentLegion;

    public Chapter(String name, String parentLegion) {
        this.name = name;
        this.parentLegion = parentLegion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentLegion() {
        return parentLegion;
    }

    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    @Override
    public int compareTo(Chapter o) {
        if ((Objects.equals(this.name, o.name)) && (Objects.equals(this.parentLegion, o.parentLegion))) {
            return 0;
        }
        else {
            return -1;
        }
    }
}
