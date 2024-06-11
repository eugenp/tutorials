package com.baeldung.injectmockintospy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MultipleInjectMockAndManualSpyWithExtensionUnitTest {
    @InjectMocks
    private BookStorageService bookStorageService;
    @InjectMocks
    private BookControlService bookControlService = Mockito.spy(BookControlService.class);
    @Mock
    private StatisticService statisticService;
    @Mock
    private RepairService repairService;

    @Test
    void whenInjectMockUsedWithManualSpy_thenMockitoCanInjectMocks() {
        Book book = new Book("Some name", "Some author", 355);
        bookStorageService.returnBook(book);

        Assertions.assertEquals(1, bookStorageService.getAvailableBooks().size());
        Mockito.verify(bookControlService).returnBook(book);
        Mockito.verify(statisticService).calculateAdded();
        Mockito.verify(repairService).shouldRepair(book);
    }
}
