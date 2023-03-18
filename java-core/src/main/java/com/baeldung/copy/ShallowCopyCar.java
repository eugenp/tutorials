package main.java.com.baeldung.copy;

public class ShallowCopyCar {

    private final String model;

    private final Company company;

    public ShallowCopyCar(String model, Company company) {
        this.model = model;
        this.company = company;
    }

    public ShallowCopyCar(ShallowCopyCar carToCopyFrom) {
        this(carToCopyFrom.model, carToCopyFrom.company);
    }

    public String getModel() {
        return model;
    }

    public Company getCompany() {
        return company;
    }
}
