package com.baeldung.deepvsshallowcopy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class ShallowCopyUnitTest {

	String MISSION_STATEMENT = "To impart world-class education to computer engineers, "
			+ "enabling them to exhibit outstanding professional skills, practice ethics of highest standards and impact society "
			+ "transformations through technological innovations";

	String VISION_STATEMENT = "Lead in computing and innovation for a smart, secure and sustainable future";

	@Test
	public void whenChangingOriginalObjectsReferenceObject_thenReferenceObjectOfShallowCopyAlsoChanges() {

		Program program = new Program("Computer and Information Systems Engineering", MISSION_STATEMENT,
				VISION_STATEMENT);
		Student student = new Student("Saud", "Malik", 23, program);
		Student shallowCopyOfStudent = new Student(student.getFirstName(), student.getLastName(), student.getAge(),
				student.getProgram());
		program.setName("Software Engineering");

		assertThat(student.getProgram().getName()).isEqualTo(shallowCopyOfStudent.getProgram().getName());
	}

}
