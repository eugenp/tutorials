package com.baeldung.book.application.use_case;

import com.baeldung.book.application.domain.Book;
import com.baeldung.book.application.port.AddPage;
import com.baeldung.book.application.port.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class AddPageUseCase implements AddPage {

    private BookRepository bookRepository;

    public AddPageUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void withText(String bookName, String text) {
        Book book = bookRepository.getByName(bookName);
        book.addPageWithText(text);

        /*
            In the context of this example, it is not necessary to invoke the "save" method.
            When we fetch the Book object, we are working with the memory reference, so it will be updated
            automatically since we are only providing an in-memory storage.

            However, since this is an interface, in a real-world scenario we wouldn't know what component implements it.
            It is a good practice to save the changes we have made to the entity.
         */
        bookRepository.save(book);
    }
}
