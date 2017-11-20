import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;

@BelongsTo(parent = School.class, foreignKeyName = "id")
public class Student extends Model {

    static {
        validatePresenceOf("name", "age");
    }

    public Student(String name) {
        set("name", name);
    }

    public Student() {
    }
}
