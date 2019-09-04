package com.baeldung.multipledatamodules.cassandra;

import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class BookAudit {
    private String bookId;
    private String auditRecord;

    public BookAudit(String bookId, String auditRecord) {
        super();
        this.bookId = bookId;
        this.auditRecord = auditRecord;
    }

    public String getAuditRecord() {
        return auditRecord;
    }

    public void setAuditRecord(String auditRecord) {
        this.auditRecord = auditRecord;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
