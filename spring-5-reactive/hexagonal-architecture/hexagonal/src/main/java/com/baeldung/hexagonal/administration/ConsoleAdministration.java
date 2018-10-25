package com.baeldung.hexagonal.administration;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonal.domain.SweepstakeAdministration;
import com.baeldung.hexagonal.domain.SweepstakeService;
import com.baeldung.hexagonal.module.SweepstakesModule;
import com.baeldung.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.baeldung.hexagonal.sampledata.SampleData;
import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * Console interface for sweepstake administration
 */
public class ConsoleAdministration {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleAdministration.class);
	
	
	
	/**
	   * Program entry point
	   */
	  public static void main(String[] args) {
	    MongoConnectionPropertiesLoader.load();
	    Injector injector = Guice.createInjector(new SweepstakesModule());
	    SweepstakeAdministration administration = injector.getInstance(SweepstakeAdministration.class);
	    SweepstakeService service = injector.getInstance(SweepstakeService.class);
	    SampleData.submitTickets(service, 20);
	    ConsoleAdministrationSrv consoleAdministration = new ConsoleAdministrationSrvImpl(administration, LOGGER);
	    try (Scanner scanner = new Scanner(System.in)) {
	      boolean exit = false;
	      while (!exit) {
	        printMainMenu();
	        String cmd = readString(scanner);
	        if ("1".equals(cmd)) {
	          consoleAdministration.getAllSubmittedTickets();
	        } else if ("2".equals(cmd)) {
	          consoleAdministration.performSweepstake();
	        } else if ("3".equals(cmd)) {
	          consoleAdministration.resetSweepstake();
	        } else if ("4".equals(cmd)) {
	          exit = true;
	        } else {
	          LOGGER.info("Unknown command: {}", cmd);
	        }
	      }
	    }
	  }

	  private static void printMainMenu() {
	    LOGGER.info("");
	    LOGGER.info("### Sweepstake Administration Console ###");
	    LOGGER.info("(1) Show all submitted entries");
	    LOGGER.info("(2) Perform Sweepstake draw");
	    LOGGER.info("(3) Reset Sweepstake database");
	    LOGGER.info("(4) Exit");
	  }

	  private static String readString(Scanner scanner) {
	    System.out.print("> ");
	    return scanner.next();
	  }
}
