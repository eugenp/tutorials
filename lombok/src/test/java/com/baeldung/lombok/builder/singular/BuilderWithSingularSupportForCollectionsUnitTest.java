package com.baeldung.lombok.builder.singular;

import org.hamcrest.MatcherAssert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class BuilderWithSingularSupportForCollectionsUnitTest {

    @Test
    public void canAddMultipleElementsAsNewCollection() throws Exception {
        Person person = Person.builder()
                .givenName("Aaron")
                .additionalName("A")
                .familyName("Aardvark")
                .tags(Arrays.asList("fictional","incidental"))
                .build();

        assertThat(person.getTags(), containsInAnyOrder("fictional", "incidental"));
    }

    @Test
    public void canUpdateCollectionAfterBuildIfMutableCollectionPassedToBuilder() throws Exception {

        List<String> tags= new ArrayList();
        tags.add("fictional");
        tags.add("incidental");
        Person person = Person.builder()
                .givenName("Aaron")
                .additionalName("A")
                .familyName("Aardvark")
                .tags(tags)
                .build();
        person.getTags().clear();
        person.getTags().add("non-fictional");
        person.getTags().add("important");

        assertThat(person.getTags(), containsInAnyOrder("non-fictional","important"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void cannotUpdateCollectionAfterBuildIfImmutableCollectionPassedToBuilder() throws Exception {
        List<String> tags = Arrays.asList("fictional","incidental");
        Person person = Person.builder()
                .givenName("Aaron")
                .additionalName("A")
                .familyName("Aardvark")
                .tags(tags)
                .build();
        person.getTags().clear();
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

        assertThat(person.getInterests(), containsInAnyOrder("sport","history"));
    }

}
