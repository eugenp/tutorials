package com.baeldung.hexagonal.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;

import com.baeldung.hexagonal.domain.PlayerDetails;
import com.baeldung.hexagonal.domain.Sweepstake;
import com.baeldung.hexagonal.domain.SweepstakeCheckResult;
import com.baeldung.hexagonal.domain.SweepstakeId;
import com.baeldung.hexagonal.domain.SweepstakeNumbers;
import com.baeldung.hexagonal.domain.SweepstakeService;

public class SweepstakeConsoleServiceImpl implements SweepstakeConsoleService {
	private final Logger logger;
	/**
	   * Constructor
	   */
	  public SweepstakeConsoleServiceImpl(Logger logger) {
	    this.logger = logger;
	  }
	  @Override
	  public void checkTicket(SweepstakeService service, Scanner scanner) {
	    logger.info( "What is the ID of the Sweepstake entry?" );
	    String id = readString( scanner );
	    logger.info( "Give the 4 comma separated winning numbers?" );
	    String numbers = readString( scanner );
	    try {
	      String[] parts = numbers.split( "," );
	      Set<Integer> winningNumbers = new HashSet<>();
	      for (int i = 0; i < 4; i++) {
	        winningNumbers.add( Integer.parseInt( parts[i] ) );
	      }

	      final SweepstakeId sweepStakeId = new SweepstakeId( Integer.parseInt( id ) );
	      final SweepstakeNumbers sweepStakeNumbers = SweepstakeNumbers.create( winningNumbers );
	      SweepstakeCheckResult result = service.checkTicketForPrize( sweepStakeId, sweepStakeNumbers );

	      if (result.getResult().equals( SweepstakeCheckResult.CheckResult.WIN_PRIZE )) {
	        logger.info( "Congratulations! The sweepstake entry has won!" );
	      } else if (result.getResult().equals( SweepstakeCheckResult.CheckResult.NO_PRIZE )) {
	        logger.info( "Unfortunately the sweepstake entry did not win." );
	      } else {
	        logger.info( "Such sweepstake entry has not been submitted." );
	      }
	    } catch (Exception e) {
	      logger.info( "Failed checking the sweepstake entry - please try again." );
	    }
	  }
	  @Override
	  public void submitTicket(SweepstakeService service, Scanner scanner) {
	    logger.info( "What is your email address?" );
	    String email = readString( scanner );
	    logger.info( "What is your bank account number?" );
	    String account = readString( scanner );
	    logger.info( "What is your phone number?" );
	    String phone = readString( scanner );
	    PlayerDetails details = new PlayerDetails( email, account, phone );
	    logger.info( "Give 4 comma separated Sweepstake entries?" );
	    String numbers = readString( scanner );
	    try {
	      String[] parts = numbers.split( "," );
	      Set<Integer> chosen = new HashSet<>();
	      for (int i = 0; i < 4; i++) {
	        chosen.add( Integer.parseInt( parts[i] ) );
	      }
	      SweepstakeNumbers sweepstakeNumbers = SweepstakeNumbers.create( chosen );
	      Sweepstake sweepstake = new Sweepstake( new SweepstakeId(), details, sweepstakeNumbers );
	      Optional<SweepstakeId> id = service.submitTicket( sweepstake );
	      if (id.isPresent()) {
	        logger.info( "Submitted sweepstake entry with id: {}", id.get() );
	      } else {
	        logger.info( "Failed submitting sweepstake entry - please try again." );
	      }
	    } catch (Exception e) {
	    	logger.info( "Failed submitting sweepstake entry - please try again." );
	    	e.printStackTrace();
	      
	    }
	  }

	  
	  private String readString(Scanner scanner) {
		    System.out.print( "> " );
		    return scanner.next();
		  }
}

