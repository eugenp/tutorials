import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private List<String> courses;

    public Student(String name, List<String> courses) {
        this.name = name;
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCourses() {
        return courses;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    // Shallow copy method
    public Student shallowCopy() {
        return new Student(this.name, this.courses);
    }

    // Deep copy method
    public Student deepCopy() {
        List<String> coursesCopy = new ArrayList<>(this.courses);
        return new Student(this.name, coursesCopy);
    }

    @Override
    public String toString() {
        return "Student{name=" + name + ", courses=" + courses + "}";
    }

}