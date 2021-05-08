package com.baeldung.interface_vs_abstract_class;

public abstract class Vehicle {

    private String vehicleName;
    private String vehicleModel;
    private Long makeYear;

    public Vehicle(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Vehicle(String vehicleName, String vehicleModel) {
        this(vehicleName);
        this.vehicleModel = vehicleModel;
    }

    public Vehicle(String vechicleName, String vehicleModel, Long makeYear) {
        this(vechicleName, vehicleModel);
        this.makeYear = makeYear;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public Long getMakeYear() {
        return makeYear;
    }

    public void setMakeYear(Long makeYear) {
        this.makeYear = makeYear;
    }

    protected abstract void start();

    protected abstract void stop();

    protected abstract void drive();

    protected abstract void changeGear();

    protected abstract void reverse();

}
