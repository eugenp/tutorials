package com.baeldung.java.copy;

/**
 * Local company class.
 */
public class Company {

    private String name;
    private Industry industry;

    /**
     * Create a new Company.
     * @param name the name to use
     * @param industry the industry that the company is in
     */
    public Company(String name, Industry industry) {
        this.name = name;
        this.industry = industry;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
