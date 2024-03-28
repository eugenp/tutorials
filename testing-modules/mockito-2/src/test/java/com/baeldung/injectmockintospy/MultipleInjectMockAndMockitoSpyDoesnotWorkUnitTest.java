package com.baeldung.injectmockintospy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MultipleInjectMockAndMockitoSpyDoesnotWorkUnitTest {
    @InjectMocks
    private BookStorageService bookStorageService;
    @InjectMocks
    @Spy
    private BookControlService bookControlService;
    @Mock
    private StatisticService statisticService;
    @Mock
    private RepairService repairService;

    @Test
    @Disabled("test is not intended to work, it is here to show that such mock setup doesn't work")
    void whenInjectMockUsedWithMockitoSpy_thenMockitoCannotInjectObjectProperly() {
        Book book = new Book("Some name", "Some author", 355);
        bookStorageService.returnBook(book);

        Assertions.assertEquals(1, bookStorageService.getAvailableBooks().size());
        Mockito.verify(bookControlService).returnBook(book);
        Mockito.verify(statisticService).calculateAdded();
        Mockito.verify(repairService).shouldRepair(book);
    }
}
