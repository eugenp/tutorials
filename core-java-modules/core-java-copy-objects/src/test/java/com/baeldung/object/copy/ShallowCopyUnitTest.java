package com.baeldung.object.copy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ShallowCopyUnitTest {

    @Test
    void whenShallowCopying_referencesShouldPointToSameObject() {
        Author authorInReview = new Author("Hardik", "behl.hardiksingh@gmail.com");
        Author selectedAuthor = authorInReview;

        String updatedEmail = "behl.hardiksingh@baeldung.com";
        selectedAuthor.setEmail(updatedEmail);

        assertThat(authorInReview.getEmail()).isEqualTo(updatedEmail);
        assertThat(authorInReview).isSameAs(selectedAuthor);
    }

}
