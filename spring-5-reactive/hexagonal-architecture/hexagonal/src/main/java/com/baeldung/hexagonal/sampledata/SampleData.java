package com.baeldung.hexagonal.sampledata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.baeldung.hexagonal.banking.InMemoryBank;
import com.baeldung.hexagonal.domain.PlayerDetails;
import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeConstants;
import com.baeldung.hexagonal.domain.SweepstakeId;
import com.baeldung.hexagonal.domain.SweepstakeNumbers;
import com.baeldung.hexagonal.domain.SweepstakeService;

public class SampleData {
	private static final List<PlayerDetails> PLAYERS;

	
	  static {
	    PLAYERS = new ArrayList<>();
	    PLAYERS.add(new PlayerDetails("abc@guice.com", "123-909", "+1100110011"));
	    PLAYERS.add(new PlayerDetails("alag@google.com", "234-808", "+11010011"));
	    PLAYERS.add(new PlayerDetails("visa@google.com", "887-909", "+11010012"));
	    InMemoryBank wireTransfers = new InMemoryBank();
	    Random random = new Random();
	    for (int i = 0; i < PLAYERS.size(); i++) {
	      wireTransfers.setFunds(PLAYERS.get(i).getBankAccount(),
	          random.nextInt(SweepstakeConstants.PLAYER_MAX_SALDO));
	    }
	  }

	  /**
	   * Inserts sweepstake entries into the database based on the sample data
	   */
	  public static void submitTickets(SweepstakeService sweepstakeService, int numTickets) {
	    for (int i = 0; i < numTickets; i++) {
	    	Sweepstake ticket = new Sweepstake(new SweepstakeId(),
	          getRandomPlayerDetails(), SweepstakeNumbers.createRandom());
	    	sweepstakeService.submitTicket(ticket);
	    }
	  }

	  private static PlayerDetails getRandomPlayerDetails() {
	    Random random = new Random();
	    int idx = random.nextInt(PLAYERS.size());
	    return PLAYERS.get(idx);
	  }
}
