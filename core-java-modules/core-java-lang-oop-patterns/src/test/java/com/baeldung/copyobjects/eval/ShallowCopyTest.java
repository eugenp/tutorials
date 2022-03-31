package com.baeldung.copyobjects.eval;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ShallowCopyTest {

    @Test
    public void whenShallowCopy_thenObjectsShouldBeDifferent_andChildObjectShouldBeTheSame() {
        Author author = new Author("Edgar Allen Poe");
        Book original = new Book("Tales of Mystery and Imagination", "978-0-00-742022-3", author);

        Book copy = new Book(original.getTitle(), original.getIsbn(), original.getAuthor());
        copy.setTitle("The Fellowship of the Ring");
        copy.setIsbn("9780007136599");
        copy.getAuthor().setName("Tolkein");

        System.out.println("original.getTitle() = " + original.getTitle()); // Tales of Mystery and Imagination
        System.out.println("copy.getTitle() = " + copy.getTitle()); // The Fellowship of the Ring

        System.out.println("original.getIsbn() = " + original.getIsbn()); // 978-0-00-742022-3
        System.out.println("copy.getIsbn() = " + copy.getIsbn()); // 9780007136599

        System.out.println("original.getAuthor().getName() = " + original.getAuthor().getName()); // Tolkein
        System.out.println("copy.getAuthor().getName() = " + copy.getAuthor().getName()); // Tolkein

        assertThat(original).isNotSameAs(copy);
        assertThat(original.getAuthor()).isSameAs(copy.getAuthor());
    }
}
