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
            clone.food = (EatType) food.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
