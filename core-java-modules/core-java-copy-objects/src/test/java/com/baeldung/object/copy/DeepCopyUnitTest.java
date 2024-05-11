package com.baeldung.object.copy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DeepCopyUnitTest {

    @Test
    void whenDeepCopyingUsingCopyConstructor_mutableReferencesShouldBeDifferent() {
        Portal portal = new Portal("Not Baeldung");
        Author authorInReview = new Author("Hardik", "behl.hardiksingh@gmail.com", portal);
        
        Author selectedAuthor = new Author(authorInReview);

        String updatedPortal = "Baeldung";
        selectedAuthor.getPortal().setName(updatedPortal);

        assertThat(authorInReview).isNotSameAs(selectedAuthor);
        assertThat(authorInReview.getPortal()).isNotSameAs(selectedAuthor.getPortal());
        assertThat(authorInReview.getPortal().getName()).isNotSameAs(selectedAuthor.getPortal().getName());
    }
    
    @Test
    void whenDeepCopyingUsingCloneMethod_mutableReferencesShouldBeDifferent() throws Exception {
        Portal portal = new Portal("Not Baeldung");
        Author authorInReview = new Author("Hardik", "behl.hardiksingh@gmail.com", portal);
        
        Author selectedAuthor = (Author) authorInReview.clone();

        String updatedPortal = "Baeldung";
        selectedAuthor.getPortal().setName(updatedPortal);

        assertThat(authorInReview).isNotSameAs(selectedAuthor);
        assertThat(authorInReview.getPortal()).isNotSameAs(selectedAuthor.getPortal());
        assertThat(authorInReview.getPortal().getName()).isNotSameAs(selectedAuthor.getPortal().getName());
    }

}
