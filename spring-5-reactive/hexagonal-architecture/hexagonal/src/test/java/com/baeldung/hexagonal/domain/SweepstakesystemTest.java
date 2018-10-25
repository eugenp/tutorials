package com.baeldung.hexagonal.domain;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.baeldung.hexagonal.banking.WireTransfers;
import com.baeldung.hexagonal.domain.SweepstakeCheckResult.CheckResult;
import com.baeldung.hexagonal.module.SweepstakeTestingModule;
import com.baeldung.hexagonal.test.SweepstakeTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 * Test the Sweepstake system
 *
 */
class SweepstakesystemTest {

  private Injector injector;
  @Inject
  private SweepstakeAdministration administration;
  @Inject
  private SweepstakeService service;
  @Inject
  private WireTransfers wireTransfers;

  SweepstakesystemTest() {
    this.injector = Guice.createInjector(new SweepstakeTestingModule());
  }

  @BeforeEach
  void setup() {
    injector.injectMembers(this);
    // add funds to the test player's bank account
    wireTransfers.setFunds("123-12312", 100);
  }
  
  @Test
  void testSweepstake() {
    // admin resets the Sweepstake
    administration.resetSweepstake();
    assertEquals(0, administration.getAllSubmittedTickets().size());
    
    // players submit the Sweepstake
    Optional<SweepstakeId> ticket1 = service.submitTicket(SweepstakeTestUtils.createSweepstake("cvt@bbb.com",
        "123-12312", "+32425255", new HashSet<>(Arrays.asList(1, 2, 3, 4))));
    assertTrue(ticket1.isPresent());
    Optional<SweepstakeId> ticket2 = service.submitTicket(SweepstakeTestUtils.createSweepstake("ant@bac.com",
        "123-12312", "+32423455", new HashSet<>(Arrays.asList(11, 12, 13, 14))));
    assertTrue(ticket2.isPresent());
    Optional<SweepstakeId> ticket3 = service.submitTicket(SweepstakeTestUtils.createSweepstake("arg@boo.com",
        "123-12312", "+32421255", new HashSet<>(Arrays.asList(6, 8, 13, 19))));
    assertTrue(ticket3.isPresent());
    assertEquals(3, administration.getAllSubmittedTickets().size());
    
    // perform Sweepstake
    SweepstakeNumbers winningNumbers = administration.performSweepstake();

    Optional<SweepstakeId> ticket4 = service.submitTicket(SweepstakeTestUtils.createSweepstake("lucky@orb.com",
        "123-12312", "+12421255", winningNumbers.getNumbers()));
    assertTrue(ticket4.isPresent());
    assertEquals(4, administration.getAllSubmittedTickets().size());
    
    // check winners
    Map<SweepstakeId, Sweepstake> tickets = administration.getAllSubmittedTickets();
    for (SweepstakeId id: tickets.keySet()) {
      SweepstakeCheckResult checkResult = service.checkTicketForPrize(id, winningNumbers);
      assertNotEquals(CheckResult.TICKET_NOT_SUBMITTED, checkResult.getResult());
      if (checkResult.getResult().equals(CheckResult.WIN_PRIZE)) {
        assertTrue(checkResult.getPrizeAmount() > 0);
      } else if (checkResult.getResult().equals(CheckResult.WIN_PRIZE)) {
        assertEquals(0, checkResult.getPrizeAmount());
      }
    }
    
    SweepstakeCheckResult checkResult = service.checkTicketForPrize(new SweepstakeId(), winningNumbers);
    assertEquals(CheckResult.TICKET_NOT_SUBMITTED, checkResult.getResult());
    assertEquals(0, checkResult.getPrizeAmount());
  }
}
