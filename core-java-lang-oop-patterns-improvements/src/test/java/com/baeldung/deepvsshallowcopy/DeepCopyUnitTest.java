package com.baeldung.deepvsshallowcopy;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

public class DeepCopyUnitTest {

    String MISSION_STATEMENT = "To impart world-class education to computer engineers, "
            + "enabling them to exhibit outstanding professional skills, practice ethics of highest standards and impact society "
            + "transformations through technological innovations";

    String VISION_STATEMENT = "Lead in computing and innovation for a smart, secure and sustainable future";

    @Test
    public void whenChangingOriginalObjectsReferenceObject_thenReferenceObjectOfDeepCopyDoesNotChange() {

        Program program = new Program("Computer and Information Systems Engineering", MISSION_STATEMENT,
                VISION_STATEMENT);

        Student student = new Student("Saud", "Malik", 23, program);
        Student deepCopyOfStudent = new Student(student);
        program.setName("Software Engineering");
        assertThat(student.getProgram().getName()).isNotEqualTo(deepCopyOfStudent.getProgram().getName());
    }
}
