package com.baeldung.likewithregex;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.content like regexp :pattern")
    List<Message> findByContentMatchingRegex(@Param("pattern") String pattern);

    @Query("select m from Message m where m.content not like regexp :pattern")
    List<Message> findByContentNotMatchingRegex(@Param("pattern") String pattern);
}