package com.baeldung.injectmockintospy;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SingleInjectMockAndAnnotatedSpyUnitTest {
    @Spy
    @InjectMocks
    private BookControlService bookControlService;
    @Mock
    private StatisticService statisticService;
    @Spy
    private RepairService repairService;

    @Test
    void whenOneInjectMockWithSpy_thenHierarchySuccessfullyInitialized() {
        Book book = new Book("Some name", "Some author", 355, ZonedDateTime.now());
        bookControlService.returnBook(book);

        Assertions.assertNull(book.getReturnDate());
        Mockito.verify(statisticService).calculateAdded();
        Mockito.verify(repairService).shouldRepair(book);
    }
}
