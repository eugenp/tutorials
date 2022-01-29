package domain;

public class Ingredient {
    private final String name;
    private final int amount;
    private final Unit unit;


    public Ingredient(String name, int amount, Unit unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return amount + " " + unit.toString() + " " + name;
    }
}
