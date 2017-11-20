import org.javalite.activejdbc.CallbackAdapter;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.test.DBSpec;
import org.junit.Test;

import java.util.List;


public class ApplicationTests extends DBSpec {

    @Test
    public void givenStudent_shouldValidateAttributes() {
        Student student = new Student();
        the(student).shouldNotBe("valid");
        student.set("name", "Jane");
        student.set("age", 10);
        the(student).shouldBe("valid");
    }


    @Test
    public void givenStudentAndSchool_shouldStoreInDb() {

        Student student = new Student();
        student.set("name", "John");
        student.set("age", 23);
        student.saveIt();

        School school = new School();
        school.saveIt();
        school.add(student);

        a(Student.count()).shouldBeEqual(1);

        List<Student> students = Student.findAll();
        the(students.get(0).get("name")).shouldBeEqual("John");
    }

    @Test
    public void givenStudentInDB_whenFind_thenCallback() {
        CallbackAdapter adapter = new CallbackAdapter() {
            @Override
            public void afterLoad(Model m) {
                the(m.get("name")).shouldBeEqual("John");
                the(m.get("age")).shouldBeEqual(23);
            }
        };
        Student.callbackWith(adapter);
        Student another = Student.findFirst("name = ?", "John");
    }

}
