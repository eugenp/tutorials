package baeldung.service;

import baeldung.model.BookStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibraryServiceImpl extends AbstractLibraryService {

    @Autowired
    public LibraryServiceImpl(BookStorage bookStorage, BookProviderNotificationService bookProviderNotificationService) {
        super(bookStorage, bookProviderNotificationService);
    }

}
