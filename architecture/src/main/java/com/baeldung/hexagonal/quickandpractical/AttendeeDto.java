package baeldung.hexagonal.core;

public class AttendeeDto {
    private Long id;
    private String name;
    private boolean isPresent;

    //getters and setters here


    public Long getId() {
        return id;
    }

    public AttendeeDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AttendeeDto setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public AttendeeDto setPresent(boolean present) {
        isPresent = present;
        return this;
    }
}
