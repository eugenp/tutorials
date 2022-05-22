package main.java.com.baeldung.tutorials;

public class Processor implements Cloneable {

	private String company;
	private String version;

	public Processor(String company, String version) {
		this.company = company;
		this.version = version;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}