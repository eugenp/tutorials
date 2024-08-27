import java.util.ArrayList;

public class Class {
    private String subject;
    private ArrayList<String> students;
    private String day;

    public Class(String subject, ArrayList<String> students, String day) {
        this.subject = subject;
        this.students = students;
        this.day = day;
    }

    public String getSubject() {
        return subject;
    }

    public String getDay() {
        return day;
    }

    public ArrayList<String> getStudents() {
        return students;
    }
}
