package com.baeldung.multipledatamodules.cassandra;

import java.util.Objects;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table
public class BookAudit {
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private String bookId;
    @PrimaryKeyColumn
    private String rentalRecNo;
    private String loaner;
    private String loanDate;

    public BookAudit(String bookId, String rentalRecNo, String loaner, String loanDate) {
        this.bookId = bookId;
        this.rentalRecNo = rentalRecNo;
        this.loaner = loaner;
        this.loanDate = loanDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getRentalRecNo() {
        return rentalRecNo;
    }

    public void setRentalRecNo(String rentalRecNo) {
        this.rentalRecNo = rentalRecNo;
    }

    public String getLoaner() {
        return loaner;
    }

    public void setLoaner(String loaner) {
        this.loaner = loaner;
    }

    public String getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(String loanDate) {
        this.loanDate = loanDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, loanDate, loaner, rentalRecNo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BookAudit))
            return false;
        BookAudit other = (BookAudit) obj;
        return Objects.equals(bookId, other.bookId) && Objects.equals(loanDate, other.loanDate) && Objects.equals(loaner, other.loaner) && Objects.equals(rentalRecNo, other.rentalRecNo);
    }

    @Override
    public String toString() {
        return "BookAudit [bookId=" + bookId + ", rentalRecNo=" + rentalRecNo + ", loaner=" + loaner + ", loanDate=" + loanDate + "]";
    }

}
