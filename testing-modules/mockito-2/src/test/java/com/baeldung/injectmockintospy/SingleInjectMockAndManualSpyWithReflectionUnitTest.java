package com.baeldung.injectmockintospy;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SingleInjectMockAndManualSpyWithReflectionUnitTest {
    @InjectMocks
    private BookStorageService bookStorageService;
    @Mock
    private StatisticService statisticService;
    @Mock
    private RepairService repairService;
    private BookControlService bookControlService;

    @BeforeEach
    public void openMocks() throws Exception {
        bookControlService = Mockito.spy(new BookControlService(statisticService, repairService));
        injectSpyToTestedMock(bookStorageService, bookControlService);
    }

    private void injectSpyToTestedMock(BookStorageService bookStorageService, BookControlService bookControlService) throws NoSuchFieldException, IllegalAccessException {
        Field bookControlServiceField = BookStorageService.class.getDeclaredField("bookControlService");
        bookControlServiceField.setAccessible(true);
        bookControlServiceField.set(bookStorageService, bookControlService);
    }

    @Test
    void whenManualSpyInjectedToTestesClass_thenHierarchySuccessfullyInitialized() {
        Book book = new Book("Some name", "Some author", 355);
        bookStorageService.returnBook(book);

        Assertions.assertEquals(1, bookStorageService.getAvailableBooks().size());
        Mockito.verify(bookControlService).returnBook(book);
        Mockito.verify(statisticService).calculateAdded();
        Mockito.verify(repairService).shouldRepair(book);
    }
}
