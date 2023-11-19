import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Struct;

@Embeddable
@Struct(name = "employee_location")
public record Location(@Column(name = "emp_country") String country, String city, String street) {
}
