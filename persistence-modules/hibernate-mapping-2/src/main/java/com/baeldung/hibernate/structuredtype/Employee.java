import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Struct;

@Entity
public class Employee {
    @Id
    private Long id;
    private Name name;
    private Location location;
    private UnorderedPojo unorderedPojo;
    private UnorderedRecord unorderedRecord;

    public Employee(Long id, Name name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
