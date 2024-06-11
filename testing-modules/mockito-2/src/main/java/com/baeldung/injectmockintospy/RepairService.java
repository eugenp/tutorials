package com.baeldung.injectmockintospy;

import lombok.Getter;

@Getter
public class RepairService {

    public boolean shouldRepair(Book book) {
        return book.getTimesTaken() > 10;
    }

}
