import java.util.ArrayList;
import java.util.List;

class DeepCopyTest {

    @org.junit.jupiter.api.Test
    void whenCloningAClass_thenReturnDeepCopy() {
        List<String> hobbies = new ArrayList<>();
        hobbies.add("reading");
        hobbies.add("swimming");
        DeepCopy.Person person1 = new DeepCopy.Person("Alice", 25, hobbies);
        DeepCopy.Person person2 = person1.deepCopy();

        assert (person2.name.equals(person1.name));
        assert (person2.age == person1.age);
        assert (person2.hobbies.equals(person1.hobbies));

        person1.name = "Bob";
        person1.hobbies.add("dancing");

        assert (!person2.name.equals(person1.name));
        assert (person2.age == person1.age);
        assert (!person2.hobbies.equals(person1.hobbies));
    }

}