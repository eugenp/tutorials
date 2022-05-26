package main.java.com.baeldung.tutorials;

// Computer class with Shallow copy implementation using clone() method
public class Computer implements Cloneable {
	private int memory;
	private String model;
	private Processor processor;

	public Computer(int memory, String model, Processor processor) {
		super();
		this.memory = memory;
		this.model = model;
		this.processor = processor;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Processor getProcessor() {
		return processor;
	}

	public void setProcessor(Processor processor) {
		this.processor = processor;
	}

	// Shallow copy Computer object
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	// Deep copy Computer object
	//	@Override
	//	public Object clone() throws CloneNotSupportedException
	//	{
	//		Computer clonedComputer = (Computer)super.clone();
	//		clonedComputer.processor = (Processor)this.processor.clone();
	//		return clonedComputer;
	//	}

	@Override
	public String toString() {
		return "\nMemory: " + memory  
				+ "\nModel: " + model 
				+ "\nProcessor company: " + processor.getCompany() 
				+ "\nProcessor version: " + processor.getVersion();
	}
}