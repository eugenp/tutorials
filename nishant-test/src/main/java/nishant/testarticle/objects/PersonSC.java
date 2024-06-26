package nishant.testarticle.objects;

/**
 * A shallow copy implementation of Person Class.
 * Person ShallowCopy is abbreviated to PersonSC.
 */
public class PersonSC implements Cloneable {

    private String name;
    private int age;
    private AddressSC address;

    @Override
    public PersonSC clone() throws CloneNotSupportedException {
        return (PersonSC) super.clone();
    }

    public PersonSC(String name, int age, AddressSC address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public PersonSC(PersonSC sourcePerson) {
        name = sourcePerson.getName();
        age = sourcePerson.getAge();
        address = sourcePerson.getAddress();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public AddressSC getAddress() {
        return address;
    }

    public void setAddress(AddressSC address) {
        this.address = address;
    }
}
