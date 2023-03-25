import java.util.ArrayList;
import java.util.List;

public class DeepCopy {
    public static class Person {
        public String name;
        public int age;
        public List<String> hobbies;

        public Person(String name, int age, List<String> hobbies) {
            this.name = name;
            this.age = age;
            this.hobbies = hobbies;
        }

        public Person deepCopy() {
            List<String> hobbiesCopy = new ArrayList<>(hobbies);
            return new Person(new String(name), age, hobbiesCopy);
        }
    }
}

