import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class whenCopyAltersOriginal_thenShallowCopy{
    @Test
    public static void main(String[] args){
        Child first_born = new Child("Sarah", 7, "female");
 
        Parent parent1 = new Parent("John", 28, first_born);
 
        Parent parent2 = null;
 
        try
        {
 
            parent2 = (Parent) parent1.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
 

        assertEquals("Sarah", parent1.child.name);

        parent2.child.name = "Jake";
        
        assertEquals("Sarah", parent1.child.name);
        
    }
}

class Child
{
    String name;
 
    int age;
 
    String sex;
 
    public Child(String val1, int val2, String val3)
    {
        this.name = val1;
 
        this.age = val2;
 
        this.sex = val3;
    }
}
 
class Parent implements Cloneable
{
    String name;
    
    int age;
 
    Child child;
 
    public Parent(String name, int age, Child child)
    {
        this.name = name;
 
        this.age = age;
 
        this.child = child;
    }
 
    // This default version of clone() method creates shallow copy of an object.
 
    protected Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}