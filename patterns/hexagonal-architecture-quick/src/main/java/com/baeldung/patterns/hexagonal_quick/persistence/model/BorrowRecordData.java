package com.baeldung.patterns.hexagonal_quick.persistence.model;

import java.util.Collection;
import java.util.Collections;

import com.baeldung.patterns.hexagonal_quick.domain.BorrowedBook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document("borrowRecord")
public class BorrowRecordData {
    @Id
    private String borrowId;
    private String username;
    private Collection<BorrowedBookData> borrowedBooks;

    public static BorrowRecordData createForOneBorrow(String borrowId, String username, BorrowedBook borrowedBook) {
        return new BorrowRecordData(
                borrowId, username, Collections.singletonList(BorrowedBookData.createFrom(borrowedBook)));
    }
}
