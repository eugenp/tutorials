package com.baeldung.lombok.builder.inheritance.superbuilder;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class BuilderInheritanceUsingSuperBuilderUnitTest {

    @Test
    public void givenSuperBuilderOnParentAndOnChild_ChildInheritingParentIsBuilt() {
        Child child = Child.builder()
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

    @Test
    public void givenSuperBuilderOnParent_StandardBuilderIsBuilt() {
        Parent parent = Parent.builder()
                .parentName("Andrea")
                .parentAge(38)
                .build();

        assertThat(parent.getParentName()).isEqualTo("Andrea");
        assertThat(parent.getParentAge()).isEqualTo(38);
    }

    @Test
    public void givenToBuilderIsSetToTrueOnParentAndChild_DeepCopyViaBuilderIsPossible() {
        Child child1 = Child.builder()
                .parentName("Andrea")
                .parentAge(38)
                .childName("Emma")
                .childAge(6)
                .build();

        Child child2 = child1.toBuilder()
                .childName("Anna")
                .build();

        assertThat(child2.getChildName()).isEqualTo("Anna");
        assertThat(child2.getChildAge()).isEqualTo(6);
        assertThat(child2.getParentName()).isEqualTo("Andrea");
        assertThat(child2.getParentAge()).isEqualTo(38);

    }



}
