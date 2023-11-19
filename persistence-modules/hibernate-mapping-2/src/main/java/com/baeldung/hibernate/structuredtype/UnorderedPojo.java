import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Struct;

@Struct(name = "unordered_pojo")
@Embeddable
public class UnorderedPojo {
    private String attribute2;
    private String attribute1;
}
