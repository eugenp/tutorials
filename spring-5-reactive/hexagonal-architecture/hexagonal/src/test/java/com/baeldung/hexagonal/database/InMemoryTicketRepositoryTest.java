package com.baeldung.hexagonal.database;

import java.util.Optional;

import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeId;
import com.baeldung.hexagonal.test.SweepstakeTestUtils;
import com.baeldung.hexagonal.database.SweepstakesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 * Tests for {@link SweepstakeRepository}
 *
 */
class InMemoryTicketRepositoryTest {

  private final SweepstakesRepository repository = new InMemoryTicketRepository();
  
  @BeforeEach
  void clear() {
    repository.deleteAll();
  }
  
  @Test
  void testCrudOperations() {
    SweepstakesRepository repository = new InMemoryTicketRepository();
    assertTrue(repository.findAll().isEmpty());
    Sweepstake ticket = SweepstakeTestUtils.createSweepstake();
    Optional<SweepstakeId> id = repository.save(ticket);
    assertTrue(id.isPresent());
    assertEquals(1, repository.findAll().size());
    Optional<Sweepstake> optionalTicket = repository.findById(id.get());
    assertTrue(optionalTicket.isPresent());
  }
}
