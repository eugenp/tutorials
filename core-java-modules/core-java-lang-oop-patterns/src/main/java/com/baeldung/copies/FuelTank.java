package com.baeldung.copies;

public class FuelTank implements Cloneable {

    private int gasMl;

    public FuelTank(int gasMl) {
        this.gasMl = gasMl;
    }

    public FuelTank(FuelTank fuelTank) {
        this.gasMl = fuelTank.gasMl;
    }

    public int getGasMl() {
        return gasMl;
    }

    public void setGasMl(int gasMl) {
        this.gasMl = gasMl;
    }

    public void fuel(int ml) {
        this.gasMl += ml;
    }

    @Override
    public FuelTank clone() {
        try {
            return (FuelTank) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new IllegalStateException(cloneNotSupportedException);
        }
    }

}
