package com.baeldung.measurement;

import javax.measure.Quantity;
import javax.measure.quantity.Volume;

public class WaterTank {

    private Quantity<Volume> capacityMeasure;
    private double capacityDouble;

    public void setCapacityMeasure(Quantity<Volume> capacityMeasure) {
        this.capacityMeasure = capacityMeasure;
    }

    public void setCapacityDouble(double capacityDouble) {
        this.capacityDouble = capacityDouble;
    }

    public Quantity<Volume> getCapacityMeasure() {
        return capacityMeasure;
    }

    public double getCapacityDouble() {
        return capacityDouble;
    }
}
