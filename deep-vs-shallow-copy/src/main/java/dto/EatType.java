package dto;

public class EatType implements Cloneable {

    public String foodType;

    public EatType(String foodType) {
        this.foodType = foodType;
    }

    @Override
    public EatType clone() {
        try {
            EatType clone = (EatType) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
