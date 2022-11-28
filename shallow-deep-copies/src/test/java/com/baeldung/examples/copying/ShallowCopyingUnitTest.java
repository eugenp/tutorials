package com.baeldung.examples.copying;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShallowCopyingUnitTest {

    private final CopyExample<Person> shallowCopier = new ShallowCopying();

    @Test
    void givenSourcePerson_whenShallowCopyIsCreated_thenExpectOnlyRootObjectCopy() {
        //arrange
        Person johnDoe = TestsFixture.johnDoe();
        Person janeDoe = TestsFixture.johnDoe();
        johnDoe.setBestFriend(janeDoe);

        //act
        Person johnCopy = shallowCopier.copy(johnDoe);

        //assert
        assertThat(johnCopy)
                .withFailMessage("Referenced Person in heap should be different")
                .matches(it -> System.identityHashCode(it) != System.identityHashCode(johnDoe))
                .withFailMessage("And objects should be equal")
                .isEqualTo(johnDoe)
                .withFailMessage("But person friend should be copied using reference copy")
                .extracting(Person::getBestFriend)
                .matches(it -> System.identityHashCode(it) == System.identityHashCode(janeDoe));
    }

}