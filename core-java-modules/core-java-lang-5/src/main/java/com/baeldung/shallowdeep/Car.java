package com.baeldung.shallowdeep;

public class Car implements Cloneable{

    private int modelNumber;
	private String modelName;
	private Engine engine;
	public Car(int modelNumber, Engine engine, String modelName) {
		super();
		this.modelNumber = modelNumber;
		this.engine = engine;
		this.modelName = modelName;
	}
	public Car(Car car) {
		this.modelNumber=car.modelNumber;
		this.modelName=car.modelName;
		this.engine=new Engine(car.getEngine());
	}
	
	public int getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(int modelNumber) {
		this.modelNumber = modelNumber;
	}
	public Engine getEngine() {
		return engine;
	}
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	@Override
	public Object clone() {
		Car car=null;
		try {
			car = (Car)super.clone();
		}
		catch(CloneNotSupportedException ex) {
			car = new Car(this.modelNumber,this.engine,this.modelName);
		}
		car.setEngine((Engine)this.engine.clone());
		return car;
	}
}
