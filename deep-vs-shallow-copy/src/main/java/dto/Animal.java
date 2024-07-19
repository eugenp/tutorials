package dto;

public class Animal implements Cloneable {
    public String name;
    public EatType food;

    public Animal(String name, EatType food) {
        this.name = name;
        this.food = food;
    }

    @Override
    public Animal clone() {
        try {
            Animal clone = (Animal) super.clone();
            clone.food = food.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
