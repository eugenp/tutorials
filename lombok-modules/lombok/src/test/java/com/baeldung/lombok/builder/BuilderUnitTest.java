package com.baeldung.lombok.builder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BuilderUnitTest {

    @Test
    public void givenBuilder_WidgetIsBuilt() {
        Widget testWidget = Widget.builder()
            .name("foo")
            .id(1)
            .build();
        assertThat(testWidget.getName()).isEqualTo("foo");
        assertThat(testWidget.getId()).isEqualTo(1);
    }

    @Test
    public void givenToBuilder_whenToBuilder_BuilderIsCreated() {

        Widget testWidget = Widget.builder()
            .name("foo")
            .id(1)
            .build();
        Widget.WidgetBuilder widgetBuilder = testWidget.toBuilder();

        Widget newWidget = widgetBuilder.id(2)
            .build();
        assertThat(newWidget.getName()).isEqualTo("foo");
        assertThat(newWidget.getId()).isEqualTo(2);
    }

    @Test
    public void givenBuilderMethod_ClientIsBuilt() {
        ImmutableClient testImmutableClient = ClientBuilder.builder()
            .name("foo")
            .id(1)
            .build();
        assertThat(testImmutableClient.getName()).isEqualTo("foo");
        assertThat(testImmutableClient.getId()).isEqualTo(1);
    }

    @Test
    public void whenUsingCustomBuilder_thenExcludeUnspecifiedFields() {
        ClassWithExcludedFields myObject = ClassWithExcludedFields.customBuilder()
            .id(3)
            .includedField("Included Field")
            // .excludedField() no method to set excludedField
            .build();

        assertThat(myObject.getId()).isEqualTo(3);
        assertThat(myObject.getIncludedField()).isEqualTo("Included Field");
    }

    @Test
    public void whenUsingBuilderDefaultAnnotation_thenExcludeField() {
        ClassWithExcludedFields myObject = ClassWithExcludedFields.builder()
            .id(3)
            .includedField("Included Field")
            .build();

        assertThat(myObject.getId()).isEqualTo(3);
        assertThat(myObject.getIncludedField()).isEqualTo("Included Field");
        assertThat(myObject.getExcludedField()).isEqualTo("Excluded Field using Default");
    }

}
