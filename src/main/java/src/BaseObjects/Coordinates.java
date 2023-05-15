package src.BaseObjects;

import java.io.Serializable;

public class Coordinates implements Serializable
{
    private Float x;
    private double y;

    public Coordinates(Float x, double y) {
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
