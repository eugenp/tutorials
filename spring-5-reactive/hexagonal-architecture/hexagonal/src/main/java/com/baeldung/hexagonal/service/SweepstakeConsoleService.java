package com.baeldung.hexagonal.service;

import java.util.Scanner;

import com.baeldung.hexagonal.domain.SweepstakeService;

public interface SweepstakeConsoleService {
	 void checkTicket(SweepstakeService service, Scanner scanner);

	  /**
	  * Submit Entry to participate in the sweepstake
	  */
	  void submitTicket(SweepstakeService service, Scanner scanner);
}
