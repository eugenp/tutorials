package testjUnit;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class JUnitTest{
    
  private static final String GPA = "The GPA of a student is 3.5";
  private static final String GPA_ONE = "The GPA of a student is 3.0";
    
  @Test
  public void Singleton(){
    final ApplicationContext context = new AnnotationConfigApplicationContext(Student.class);

    final Student studentGpa = (Student) context.getBean(Student.class);
    final Student studentGpa1 = (Student) context.getBean(Student.class);
    
    studentGpa.setGpa(GPA);
    Assert.assertEquals(GPA, studentGpa.getGpa());
    
 }
  
  @Test
  public void Prototype()
  {
    final ApplicationContext context = new AnnotationConfigApplicationContext(Student.class);
    
    final Student studentGpa = (Student) context.getBean(Student.class);
    final Student studentGpa1 = (Student) context.getBean(Student.class);
    
    studentGpa.setGpa(GPA);
    studentGpa1.setGpa(GPA_ONE);
    
    Assert.assertEquals(GPA, studentGpa.getGpa());
    Assert.assertEquals(GPA_ONE, studentGpa1.getGpa());
   
    
  }
  
}
