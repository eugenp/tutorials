package hexagonal.demo.domain;

public class RabbitStore {

    private long id;
    private String name;
    private String species;
    private int price;
    private String description;
    private boolean isAvailable;

    public RabbitStore() {
    }

    public RabbitStore(long id, String name, String species, int price, String description, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.price = price;
        this.description = description;
        this.isAvailable = isAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getPrice() {
        if (price < 0) {
            return 0;
        } else {

            return price;
        }
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
