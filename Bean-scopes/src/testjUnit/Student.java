package testjUnit;

import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;

@Component
@Scope("prototype")

public class Student 
{       
        
        private String gpa;

        public Student()
        {
            
        }
        
        public String getGpa()
        {
            return gpa;
        }
        
        public void setGpa(String gpa)
        {
            this.gpa = gpa;
        }
}