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

    @Test
    public void givenSuperBuilderOnAllThreeLevels_StudentInheritingChildAndParentIsBuilt() {
        Student student = Student.builder()
                .parentName("Andrea")
                .parentAge(38)
                .childName("Emma")
                .childAge(6)
                .schoolName("Baeldung High School")
                .build();

        assertThat(student.getChildName()).isEqualTo("Emma");
        assertThat(student.getChildAge()).isEqualTo(6);
        assertThat(student.getParentName()).isEqualTo("Andrea");
        assertThat(student.getParentAge()).isEqualTo(38);
        assertThat(student.getSchoolName()).isEqualTo("Baeldung High School");
    }

    @Test
    public void givenToBuilderIsSetToTrueOnParentChildAndStudent_DeepCopyViaBuilderIsPossible() {
        Student student1 = Student.builder()
                .parentName("Andrea")
                .parentAge(38)
                .childName("Emma")
                .childAge(6)
                .schoolName("School 1")
                .build();

        Student student2 = student1.toBuilder()
                .childName("Anna")
                .schoolName("School 2")
                .build();

        assertThat(student2.getChildName()).isEqualTo("Anna");
        assertThat(student2.getChildAge()).isEqualTo(6);
        assertThat(student2.getParentName()).isEqualTo("Andrea");
        assertThat(student2.getParentAge()).isEqualTo(38);
        assertThat(student2.getSchoolName()).isEqualTo("School 2");

    }


}
