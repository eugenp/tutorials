package main.java.com.baeldung.copy;

public class DeepCopyCar {

    private final String model;

    private final Company company;

    public DeepCopyCar(String model, Company company) {
        this.model = model;
        this.company = company;
    }

    public DeepCopyCar(DeepCopyCar carToCopyFrom) {
        this(carToCopyFrom.model, new Company(carToCopyFrom.company.getName()));
    }

    public String getModel() {
        return model;
    }

    public Company getCompany() {
        return company;
    }
}
