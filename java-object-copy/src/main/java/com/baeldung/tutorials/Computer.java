package main.java.com.baeldung.tutorials;

public class Computer implements Cloneable {
	private int type;
	private int memory;
	private  String manufacturingYear;
	private String model;
	private Processor processor;

	public Computer(int type, int memory, String manufacturingYear, String model, Processor processor) {
		super();
		this.type = type;
		this.memory = memory;
		this.manufacturingYear = manufacturingYear;
		this.model = model;
		this.processor = processor;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public String getManufacturingYear() {
		return manufacturingYear;
	}

	public void setManufacturingYear(String manufacturingYear) {
		this.manufacturingYear = manufacturingYear;
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
//		@Override
//		public Object clone() throws CloneNotSupportedException
//		{
//		    Computer clonedComputer = (Computer)super.clone();
//		    clonedComputer.processor = (Processor)this.processor.clone();
//		    return clonedComputer;
//		}

	@Override
	public String toString() {
		return "Type: " + type 
			+ "\nMemory: " + memory 
			+ "\nManufacturing Year: " + manufacturingYear 
			+ "\nModel: " + model 
			+ "\nProcessor company: " + processor.getCompany() 
			+ "\nProcessor version: " + processor.getVersion();
	}
}