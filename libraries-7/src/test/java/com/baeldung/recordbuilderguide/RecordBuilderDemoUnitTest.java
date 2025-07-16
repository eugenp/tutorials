package com.baeldung.recordbuilderguide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecordBuilderDemoUnitTest {

    @Test
    void givenNoInput_whenCreateInitialPerson_thenCorrectFieldsSet() {
        Person p1 = RecordBuilderDemo.createInitialPerson();
        assertEquals("foo", p1.name());
        assertEquals(123, p1.age());
    }

    @Test
    void givenPerson_whenUpdateName_thenNameChangesAndAgeUnchanged() {
        Person p1 = RecordBuilderDemo.createInitialPerson();
        Person p2 = RecordBuilderDemo.updateName(p1);

        assertEquals("bar", p2.name());
        assertEquals(123, p2.age());
    }

    @Test
    void givenPerson_whenUpdateAge_thenAgeChangesAndNameUnchanged() {
        Person p2 = RecordBuilderDemo.updateName(RecordBuilderDemo.createInitialPerson());
        Person p3 = RecordBuilderDemo.updateAge(p2);

        assertEquals("bar", p3.name());
        assertEquals(456, p3.age());
    }

    @Test
    void givenPerson_whenUpdateBothFieldsWithBuilder_thenBothFieldsChange() {
        Person p3 = RecordBuilderDemo.updateAge(
                        RecordBuilderDemo.updateName(
                            RecordBuilderDemo.createInitialPerson()));
        Person p4 = RecordBuilderDemo.updateBothFieldsWithBuilder(p3);

        assertEquals("baz", p4.name());
        assertEquals(101, p4.age());
    }

    @Test
    void givenPerson_whenUpdateWithConsumer_thenFieldsChangeCorrectly() {
        Person p4 = RecordBuilderDemo.updateBothFieldsWithBuilder(
                        RecordBuilderDemo.updateAge(
                            RecordBuilderDemo.updateName(
                                RecordBuilderDemo.createInitialPerson())));
        Person p5 = RecordBuilderDemo.updateWithConsumer(p4);

        assertEquals("whatever", p5.name());
        assertEquals(200, p5.age());
    }

    @Test
    void givenPerson_whenUpdateWithConditionalConsumer_thenNameIsConditionallySet() {
        Person p5 = RecordBuilderDemo.updateWithConsumer(
                        RecordBuilderDemo.updateBothFieldsWithBuilder(
                            RecordBuilderDemo.updateAge(
                                RecordBuilderDemo.updateName(
                                    RecordBuilderDemo.createInitialPerson()))));
        Person p6 = RecordBuilderDemo.updateWithConditionalConsumer(p5);

        assertEquals("Teen whatever", p6.name());
        assertEquals(200, p6.age());
    }

    @Test
    void givenPerson_whenUpdateWithStaticBuilderAndConsumer_thenFieldsAreUpdated() {
        Person p6 = RecordBuilderDemo.updateWithConditionalConsumer(
                        RecordBuilderDemo.updateWithConsumer(
                            RecordBuilderDemo.updateBothFieldsWithBuilder(
                                RecordBuilderDemo.updateAge(
                                    RecordBuilderDemo.updateName(
                                        RecordBuilderDemo.createInitialPerson())))));
        Person p7 = RecordBuilderDemo.updateWithStaticBuilderAndConsumer(p6);

        assertEquals("Manual Copy", p7.name());
        assertEquals(300, p7.age());
    }

    @Test
    void givenPerson_whenUpdateWithStaticBuilderAndName_thenOnlyNameChanges() {
        Person p6 = RecordBuilderDemo.updateWithConditionalConsumer(
                        RecordBuilderDemo.updateWithConsumer(
                            RecordBuilderDemo.updateBothFieldsWithBuilder(
                                RecordBuilderDemo.updateAge(
                                    RecordBuilderDemo.updateName(
                                        RecordBuilderDemo.createInitialPerson())))));
        Person p8 = RecordBuilderDemo.updateWithStaticBuilderAndName(p6);

        assertEquals("boop", p8.name());
        assertEquals(200, p8.age());
    }
}
