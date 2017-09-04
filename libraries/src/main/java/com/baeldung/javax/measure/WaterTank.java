package com.baeldung.javax.measure;

import javax.measure.Measure;
import javax.measure.quantity.Volume;

public class WaterTank {

    private Measure<Volume> capacityMeasure;
    private double capacityDouble;

    public void setCapacityMeasure(Measure<Volume> capacityMeasure) {
        this.capacityMeasure = capacityMeasure;
    }

    public void setCapacityDouble(double capacityDouble) {
        this.capacityDouble = capacityDouble;
    }

    public Measure<Volume> getCapacityMeasure() {
        return capacityMeasure;
    }

    public double getCapacityDouble() {
        return capacityDouble;
    }
}
