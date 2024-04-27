package com.baeldung.object.copy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DeepCopyUnitTest {

	@Test
	void whenDeepCopying_referencesShouldPointToDifferentObjects() {
		Author authorInReview = new Author("Hardik", "behl.hardiksingh@gmail.com");
		Author selectedAuthor = new Author(authorInReview);

		String updatedEmail = "behl.hardiksingh@baeldung.com";
		selectedAuthor.setEmail(updatedEmail);

		assertThat(authorInReview.getEmail()).isNotEqualTo(updatedEmail);
		assertThat(authorInReview).isNotSameAs(selectedAuthor);
	}

}
