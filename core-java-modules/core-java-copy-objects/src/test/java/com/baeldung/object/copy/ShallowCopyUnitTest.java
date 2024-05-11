package com.baeldung.object.copy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ShallowCopyUnitTest {

    @Test
    void whenShallowCopying_mutablePropertyReferencesAreCopied(){
        Portal portal = new Portal("Not Baeldung");
        Author authorInReview = new Author("Hardik", "behl.hardiksingh@gmail.com", portal);

        Author selectedAuthor = new Author(authorInReview.getName(), authorInReview.getEmail(), authorInReview.getPortal());

        String updatedPortal = "Baeldung";
        selectedAuthor.getPortal().setName(updatedPortal);

        assertThat(authorInReview).isNotSameAs(selectedAuthor);
        assertThat(authorInReview.getPortal().getName()).isEqualTo(updatedPortal);
    }

}
