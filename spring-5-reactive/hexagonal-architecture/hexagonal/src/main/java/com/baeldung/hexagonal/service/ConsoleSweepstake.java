package com.baeldung.hexagonal.service;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonal.domain.SweepstakeService;
import com.baeldung.hexagonal.module.SweepstakesModule;
import com.baeldung.hexagonal.mongo.MongoConnectionPropertiesLoader;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class ConsoleSweepstake {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConsoleSweepstake.class);

	  /**
	   * Program entry point
	   */
	public static void main(String[] args) {
	    MongoConnectionPropertiesLoader.load();
	    Injector injector = Guice.createInjector(new SweepstakesModule());
	    SweepstakeService service = injector.getInstance(SweepstakeService.class);
	   // WireTransfers bank = injector.getInstance(WireTransfers.class);
	    try (final Scanner scanner = new Scanner(System.in)) {
	      boolean exit = false;
	      while (!exit) {
	        printMainMenu();
	        String cmd = readString(scanner);
	        SweepstakeConsoleService lotteryConsoleService = new SweepstakeConsoleServiceImpl(LOGGER);
	        if ("1".equals(cmd)) {
	          lotteryConsoleService.submitTicket(service, scanner);
	        } else if ("2".equals(cmd)) {
	          lotteryConsoleService.checkTicket(service, scanner);
	        } else if ("3".equals(cmd)) {
	          exit = true;
	        } else {
	          LOGGER.info("Unknown command");
	        }
	      }
	    }
	  }

	  private static void printMainMenu() {
	    LOGGER.info("");
	    LOGGER.info("### Sweepstake Service Console ###");
	    LOGGER.info("1) Submit Entry");
	    LOGGER.info("(2) Check Entry");
	    LOGGER.info("(3) Exit");
	  }

	  private static String readString(Scanner scanner) {
	    System.out.print("> ");
	    return scanner.next();
	  }
}
