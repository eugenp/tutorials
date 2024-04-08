public class Vehicle implements Cloneable {
    public String name;
    public String color;

    public Vehicle(String name, String color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}