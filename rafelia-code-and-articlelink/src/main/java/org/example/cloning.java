public class Person implements Cloneable {
    String name;
    Food food;
    
    public Person(String name, Food food) {
        this.name = name;
        this.food = food;
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
