package baeldung.hexagonal.outside.entities;

public class MemoryAttendeeEntity {
    private Long id;
    private String name;
    private boolean isPresent;

    public Long getId() {
        return id;
    }

    public MemoryAttendeeEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MemoryAttendeeEntity setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public MemoryAttendeeEntity setPresent(boolean present) {
        isPresent = present;
        return this;
    }

    @Override
    public String toString() {
        return "MemoryAttendeeEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isPresent=" + isPresent +
                '}';
    }
}
