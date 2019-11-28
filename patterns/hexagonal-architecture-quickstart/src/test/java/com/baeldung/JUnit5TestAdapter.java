package com.baeldung;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.exception.RecordNotFoundException;
import com.baeldung.pojo.BorrowingRecord;
import com.baeldung.service.LibraryUseCase;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JUnit5TestAdapter {

    @Autowired
    private LibraryUseCase useCase;

    @ParameterizedTest
    @CsvSource(value = { "U0001,B0001", "U0001,B0002" })
    void whenBorrowSuccess_thenReturnBorrowingRecord(String uid, String bookNo) throws RecordNotFoundException {
        BorrowingRecord record = useCase.borrowBook(uid, bookNo);
        assertNotNull(record, "Borrowing Record should be created!");
    }

    @ParameterizedTest
    @ValueSource(longs = { 1 })
    void whenOverDue_thenHaveOverduePenalty(long recNo) throws RecordNotFoundException {
        BorrowingRecord record = useCase.returnBook(recNo);
        assertNotNull(record.getOverdueFines(), "Overdue Fines should not be null");
        assertTrue(record.getOverdueFines()
            .doubleValue() > 0, "Penalty should greated than 0");
    }

}
