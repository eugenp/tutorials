package org.baeldung.bean.injection;

import org.springframework.stereotype.Component;

/**
 * This is the Animal class which will be injected in Zoo class.
 * It is planned to be injected in another class, therefore it is
 * annotated with @Component
 */
@Component
public class Animal {
    int age;
    String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
