package com.baeldung.copies.shallow;

class FuelTank implements Cloneable {

    private int gasMl;

    public FuelTank(int gasMl) {
        this.gasMl = gasMl;
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

}
