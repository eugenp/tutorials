package com.baeldung.lombok.builder.singular;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BuilderWithSingularSupportForCollectionsUnitTest {

    @Test
    public void canAddMultipleElementsAsNewCollection() throws Exception {
        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .tags(Arrays.asList("fictional", "incidental"))
            .build();

        assertThat(person.getTags(), containsInAnyOrder("fictional", "incidental"));
    }

    @Test
    public void canUpdateCollectionAfterBuildIfMutableCollectionPassedToBuilder() throws Exception {

        List<String> tags = new ArrayList();
        tags.add("fictional");
        tags.add("incidental");
        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .tags(tags)
            .build();
        person.getTags()
            .clear();
        person.getTags()
            .add("non-fictional");
        person.getTags()
            .add("important");

        assertThat(person.getTags(), containsInAnyOrder("non-fictional", "important"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cannotUpdateCollectionAfterBuildIfImmutableCollectionPassedToBuilder() throws Exception {
        List<String> tags = Arrays.asList("fictional", "incidental");
        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .tags(tags)
            .build();
        person.getTags()
            .clear();
    }

    @Test
    public void canAssignToSingularAnnotatedCollectionOneByOne() throws Exception {

        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .interest("history")
            .interest("sport")
            .build();

        assertThat(person.getInterests(), containsInAnyOrder("sport", "history"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void singularAnnotatedBuilderCreatesImmutableCollection() throws Exception {

        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .interest("history")
            .interest("sport")
            .build();
        person.getInterests()
            .clear();
    }

    @Test
    public void unpopulatedListsCreatedAsNullIfNotSingularButEmptyArrayIfSingular() throws Exception {

        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .build();
        assertThat(person.getInterests(), hasSize(0));
        assertThat(person.getSkills(), hasSize(0));
        assertThat(person.getAwards()
            .keySet(), hasSize(0));
        assertThat(person.getTags(), is(nullValue()));
    }

    @Test
    public void singularSupportsSetsToo() throws Exception {

        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .skill("singing")
            .skill("dancing")
            .build();
        assertThat(person.getSkills(), contains("singing", "dancing"));
    }

    @Test
    public void singularSetsAreLenientWithDuplicates() throws Exception {

        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .interest("singing")
            .interest("singing")
            .skill("singing")
            .skill("singing")
            .build();
        assertThat(person.getInterests(), contains("singing", "singing"));
        assertThat(person.getSkills(), contains("singing"));
    }

    @Test
    public void singularSupportsMapsToo() throws Exception {

        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .award("Singer of the Year", LocalDate.now()
                .minusYears(5))
            .award("Best Dancer", LocalDate.now()
                .minusYears(2))
            .build();
        assertThat(person.getAwards()
            .keySet(), contains("Singer of the Year", "Best Dancer"));
        assertThat(person.getAwards()
            .get("Best Dancer"),
            is(LocalDate.now()
                .minusYears(2)));
    }

    @Test
    public void singularIsLenientWithMapKeys() throws Exception {

        Person person = Person.builder()
            .givenName("Aaron")
            .additionalName("A")
            .familyName("Aardvark")
            .award("Best Dancer", LocalDate.now()
                .minusYears(5))
            .award("Best Dancer", LocalDate.now()
                .minusYears(4))
            .award("Best Dancer", LocalDate.now()
                .minusYears(3))
            .award("Best Dancer", LocalDate.now()
                .minusYears(2))
            .award("Best Dancer", LocalDate.now()
                .minusYears(1))
            .build();
        assertThat(person.getAwards()
            .keySet(), hasSize(1));
        assertThat(person.getAwards()
            .get("Best Dancer"),
            is(LocalDate.now()
                .minusYears(1)));
    }

    @Test
    public void wordsWithNonStandardPlurals() throws Exception {
        Sea sea = Sea.builder()
            .grass("Dulse")
            .grass("Kelp")
            .oneFish("Cod")
            .oneFish("Mackerel")
            .build();
        assertThat(sea.getGrasses(), contains("Dulse", "Kelp"));
        assertThat(sea.getFish(), contains("Cod", "Mackerel"));
    }

}
