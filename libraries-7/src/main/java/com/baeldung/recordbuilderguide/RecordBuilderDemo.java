package com.baeldung.recordbuilderguide;

import java.util.function.Consumer;

public class RecordBuilderDemo {

    public static Person createInitialPerson() {
        return new Person("foo", 123);
    }

    public static Person updateName(Person original) {
        return original.withName("bar");
    }

    public static Person updateAge(Person original) {
        return original.withAge(456);
    }

    public static Person updateBothFieldsWithBuilder(Person original) {
        return original.with()
          .age(101)
          .name("baz")
          .build();
    }

    public static Person updateWithConsumer(Person original) {
        return original.with(p -> p.age(200).name("whatever"));
    }

    public static Person updateWithConditionalConsumer(Person original) {
        return original.with(p -> {
            if (p.age() > 13) {
                p.name("Teen " + p.name());
            } else {
                p.name("whatever");
            }
        });
    }

    public static Person updateWithStaticBuilderAndConsumer(Person original) {
        return PersonBuilder.from(original)
          .with(p -> p.age(300).name("Manual Copy"));
    }

    public static Person updateWithStaticBuilderAndName(Person original) {
        return PersonBuilder.from(original)
          .withName("boop");
    }
}
