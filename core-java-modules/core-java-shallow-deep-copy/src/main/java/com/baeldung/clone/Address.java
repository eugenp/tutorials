package core;

/**
 * Model class with
 */
class Address {
    private String city;

    public Address(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    // Setter method for city attribute
    public void setCity(String city) {
        this.city = city;
    }

}