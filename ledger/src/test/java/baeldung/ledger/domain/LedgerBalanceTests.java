package baeldung.ledger.domain;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName( "Ensure that the determination of the balance of an account is correct." )
class LedgerBalanceTests {

	@Test
	@DisplayName( "Ensure that 0 returns if the account has no entries." )
	void emptyAccount() {

		assertEquals( 0, new Ledger().balance( "empty-account" ) );
	}

	@Test
	@DisplayName( "Ensure that the credited amount is returned as the balance." )
	void creditEqualsBalance() {

		assertEquals( 100, new Ledger().enter( new Credit( 100, "acct", "test") ) );
	}

	@Test
	@DisplayName( "Ensure that the debited amount is returned as a negative amount for balance." )
	void debitEqualsBalance() {

		assertEquals( -100, new Ledger().enter( new Debit( 100, "acct", "test"  ) ) );
	}

	@Test
	@DisplayName( "Ensure that zero is returned when the same amount is debited as was credited." )
	void debitAndCreditEqualZero() {

		Ledger ledger = new Ledger();

		ledger.enter( new Credit( 100, "acct", "test" ) );
		ledger.enter( new Debit( 100, "acct", "test" ) );

		assertEquals( 0, ledger.balance( "acct" ) );
	}

	@Test
	@DisplayName( "Ensure that credits and debits equal 100." )
	void debitAndCreditToEqualOneHundred() {

		Ledger ledger = new Ledger();

		ledger.enter( new Credit( 100, "acct", "total = 100" ) );
		ledger.enter( new Debit( 50, "acct", "total = 50" ) );
		ledger.enter( new Credit( 50, "acct", "total = 100" ) );

		assertEquals( 100, ledger.balance( "acct" ) );
	}
}
