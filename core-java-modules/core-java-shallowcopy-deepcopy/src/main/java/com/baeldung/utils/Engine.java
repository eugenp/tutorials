package utils;

public class Engine implements Cloneable{
    public int horsepower;

    public Engine(int horsepower) {
        this.horsepower = horsepower;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
