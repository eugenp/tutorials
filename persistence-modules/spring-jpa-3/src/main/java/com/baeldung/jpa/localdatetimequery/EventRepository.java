package com.baeldung.jpa.localdatetimequery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    List<Event> findByCreatedAtGreaterThanEqualAndCreatedAtLessThan(
        LocalDateTime start,
        LocalDateTime end
    );


    @Query("SELECT e FROM Event e WHERE FUNCTION('DATE', e.createdAt) = :date")
    List<Event> findByDate(@Param("date") LocalDate date);

    @Query(
        value = "SELECT * FROM events " +
            "WHERE created_at >= :startOfDay " +
            "AND created_at < :endOfDay",
        nativeQuery = true
    )
    List<Event> findByDateRangeNative(
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay
    );
}