package DeepVersusShallowCopyExample;

/**
 *
 * @author Tim de Vries
 */
public class FamilyMemberCloneable implements Cloneable {
    int age;
    String firstName;
    String lastName;
    Address address;
    
    public FamilyMemberCloneable(int age, String firstName,String lastName) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Object clone() throws CloneNotSupportedException {
        Object o = super.clone();
        return o;
    }
    
    public Address getAddress() { return address; }
    
    public int getAge() { return age; }
    
    public String getFirstName() { return firstName; }
    
    public String getLastName() { return lastName; }
    
    public void setAge(int age) { this.age = age; }
    
    public void setFirstName(String newName) {
        firstName = newName;
    }
    
    public void setLastName(String newName) {
        lastName = newName;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
}
