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
    public void givenBuilderAtMethodLevel_ChildInheritingParentIsBuilt() {
        Child child = Child.childBuilder()
            .parentName("Andrea")
            .parentAge(38)
            .childName("Emma")
            .childAge(6)
            .build();

        assertThat(child.getChildName()).isEqualTo("Emma");
        assertThat(child.getChildAge()).isEqualTo(6);
        assertThat(child.getParentName()).isEqualTo("Andrea");
        assertThat(child.getParentAge()).isEqualTo(38);
    }

}
