package com.baeldung.spring.modulith.cqrs.ticket.internal;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BookedTicketRepository extends CrudRepository<BookedTicket, Long> {
    @Query("""
            SELECT b FROM BookedTicket b
            WHERE b.movieId = :movieId
            AND b.seatNumber = :seatNumber
            ORDER BY b.createdAt DESC
            LIMIT 1
        """)
    Optional<BookedTicket> findLatestByMovieIdAndSeatNumber(Long movieId, String seatNumber);

}
