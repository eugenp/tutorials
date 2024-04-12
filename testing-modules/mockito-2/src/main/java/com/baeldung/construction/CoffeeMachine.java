package com.baeldung.construction;

public class CoffeeMachine {

    private Grinder grinder;
    private WaterTank tank;

    public CoffeeMachine() {
        this.grinder = new Grinder();
        this.tank = new WaterTank();
    }
    
    public CoffeeMachine(int mils) {
        this.grinder = new Grinder();
        this.tank = new WaterTank(mils);
    }

    public String makeCoffee() {
        String type = this.tank.isEspresso() ? "Espresso" : "Americano";
        return String.format("Finished making a delicious %s made with %s beans", type, this.grinder.getBeans());
    }

}
