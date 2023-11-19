import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Struct;

@Struct(name = "unordered_record")
@Embeddable
public record UnorderedRecord(String attribute2, String attribute1) {
}
