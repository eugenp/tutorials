package dto;

public class EatType implements Cloneable {
    public String foodType;

    public EatType(String foodType) {
        this.foodType = foodType;
    }

    @Override
    public EatType clone() {
        try {
            return (EatType) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
