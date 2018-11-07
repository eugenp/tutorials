package com.baeldung;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class BookKeeperLedgerUnitTest {

  private static BookkeeperLedger bookkeeperLedger;

  @BeforeEach
  public void init() throws Exception {
    bookkeeperLedger = new BookkeeperLedger();
    bookkeeperLedger.connect();
  }


  @Test
  public void ledgerReadAndWrite() {
    long ledgerId = 0;
    try {
      ledgerId = bookkeeperLedger.createLedger();
      assert ledgerId > 0;
      bookkeeperLedger.write();
      assertThat(bookkeeperLedger.read()).isEqualTo(100);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      assert false;
    }

  }

  @AfterEach
  public void cleanUp() throws Exception {
    bookkeeperLedger.closeConnection();
  }
}
