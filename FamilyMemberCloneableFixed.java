package DeepVersusShallowCopyExample;

/**
 *
 * @author Tim de Vries
 */
public class FamilyMemberCloneableFixed implements Cloneable {
    int age;
    String firstName;
    String lastName;
    Address address;
    
    public FamilyMemberCloneableFixed(int age, String firstName,String lastName) {
        this.age = age;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Object clone() throws CloneNotSupportedException {
        FamilyMemberCloneableFixed o = (FamilyMemberCloneableFixed)super.clone();
        FamilyMemberCloneableFixed member = (FamilyMemberCloneableFixed)o;
        Address home = member.getAddress();
        Address cloneAddress = new Address(home.getAddr1(),home.getAddr2(),home.getMunicipality(),
                                    home.getProvState(),home.getCountry(),home.getZipPostal());
        o.setAddress(cloneAddress);
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
