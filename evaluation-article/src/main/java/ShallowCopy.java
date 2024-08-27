import java.util.ArrayList;

public class ShallowCopy {

    public static Class copy() {

        ArrayList<String> students = new ArrayList<>();
        students.add("Sam");
        students.add("Jane");
        students.add("John");

        Class class1 = new Class("math", students, "Monday");

        Class class2 = new Class(class1.getSubject(), class1.getStudents(), "Tuesday");
        class2.getStudents().add("Pat");

        return class2;
    }

}
