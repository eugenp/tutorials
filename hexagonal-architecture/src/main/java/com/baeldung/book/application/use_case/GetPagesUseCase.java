package com.baeldung.book.application.use_case;

import com.baeldung.book.application.domain.Page;
import com.baeldung.book.application.port.BookRepository;
import com.baeldung.book.application.port.GetPages;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPagesUseCase implements GetPages {

    private BookRepository bookRepository;

    public GetPagesUseCase(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Page> fromBook(String name) {
        return bookRepository.getByName(name).getPages();
    }
}
