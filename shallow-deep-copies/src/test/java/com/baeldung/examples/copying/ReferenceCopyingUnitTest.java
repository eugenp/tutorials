package com.baeldung.examples.copying;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

class ReferenceCopyingUnitTest {

    private final CopyExample<Person> referenceCopier = new ReferenceCopying();

    @Test
    void givenSourcePerson_whenReferenceIsCopied_expectSameObjectReferences() {
        //arrange
        Person johnDoe = TestsFixture.johnDoe();
        Person janeDoe = TestsFixture.johnDoe();
        johnDoe.setBestFriend(janeDoe);

        //act
        Person johnCopy = referenceCopier.copy(johnDoe);

        //assert
        assertSame(johnCopy, johnDoe, "In case of reference copy, references should be the same");
    }

}