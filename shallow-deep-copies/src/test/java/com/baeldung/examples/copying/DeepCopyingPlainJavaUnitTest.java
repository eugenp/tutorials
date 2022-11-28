package com.baeldung.examples.copying;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeepCopyingPlainJavaUnitTest {

    private final CopyExample<Person> plainJavaDeepCopier = new DeepCopyingPlainJava();

    @Test
    void givenSourcePerson_whenDeepCopiedUsingPlainJava_thenExpectCompleteCopy() {
        //arrange
        Person johnDoe = TestsFixture.johnDoe();
        Person janeDoe = TestsFixture.johnDoe();
        johnDoe.setBestFriend(janeDoe);

        //act
        Person johnCopy = plainJavaDeepCopier.copy(johnDoe);

        //assert
        assertThat(johnCopy)
                .withFailMessage("Referenced Person in heap should be different")
                .matches(it -> System.identityHashCode(it) != System.identityHashCode(johnDoe))
                .withFailMessage("But objects should be equal")
                .isEqualTo(johnDoe)
                .withFailMessage("And Persons friend should also have another reference to object in heap")
                .extracting(Person::getBestFriend)
                .matches(it -> System.identityHashCode(it) != System.identityHashCode(janeDoe))
                .withFailMessage("And Persons friend copy should also be equal to the source")
                .isEqualTo(janeDoe);
    }

}