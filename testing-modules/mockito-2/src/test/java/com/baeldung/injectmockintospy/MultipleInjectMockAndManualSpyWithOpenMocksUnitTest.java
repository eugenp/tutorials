package com.baeldung.injectmockintospy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class MultipleInjectMockAndManualSpyWithOpenMocksUnitTest {

    @InjectMocks
    private BookStorageService bookStorageService;
    @InjectMocks
    private BookControlService bookControlService;
    @Mock
    private StatisticService statisticService;
    @Mock
    private RepairService repairService;
    private AutoCloseable closeable;

    @BeforeEach
    public void openMocks() {
        bookControlService = Mockito.spy(BookControlService.class);
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void releaseMocks() throws Exception {
        closeable.close();
    }

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