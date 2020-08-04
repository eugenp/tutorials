package com.baeldung.hexagonal.domain;

import java.util.List;
import java.util.Calendar;
import java.util.UUID;

public class Library {
    
    private final int num_of_days = 14;
    
    private final UUID blank_UUID = new UUID(0, 0);
    
    private final UUID error_UUID = new UUID(0, -1);
    
    public UUID issueBook(Book bookEntry,
        UUID memberId,
        String memberName) {
        
        if (bookEntry.getStatus() == BookStatus.ISSUED) {
            return error_UUID;
        }
        
        bookEntry.setStatus(BookStatus.ISSUED);
        bookEntry.setMemberInfo(memberId, memberName);
        
        Calendar cal = Calendar.getInstance();
        
        bookEntry.setIssueDate(cal.getTime());
        
        cal.add(Calendar.DAY_OF_YEAR, num_of_days);
        
        bookEntry.setReturnDate(cal.getTime());
        
        return bookEntry.getBookId();
    }
    
    public void returnBook(Book bookEntry) {
        bookEntry.setStatus(BookStatus.AVAILABLE);
        bookEntry.setMemberInfo(blank_UUID, "");
    }
}
