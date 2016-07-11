package jUnitScopes;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Scope{
  public static void main(String[] Args){
	ApplicationContext context = new AnnotationConfigApplicationContext(Student.class);

    Student studentsGpa = (Student) context.getBean(Student.class);
    studentsGpa.setGpa("The GPA of student one is 3.5");
    System.out.println("Student One" + '\n' + studentsGpa.getGpa());
    
    studentsGpa = context.getBean(Student.class);
    System.out.println("Student One" + '\n' + studentsGpa.getGpa());
 }
}
