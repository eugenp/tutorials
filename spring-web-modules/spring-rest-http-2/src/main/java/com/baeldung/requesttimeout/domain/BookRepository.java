package com.baeldung.requesttimeout.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {

    default int wasteTime() {
        int i = Integer.MIN_VALUE;
        while(i < Integer.MAX_VALUE) {
            i++;
        }
        return i;
    }
}
