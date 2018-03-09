package datajpa.domain.domain3;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "teachers")
    private Set<Student> students = new HashSet<Student>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
        student.getTeachers().add(this);
    }
}
