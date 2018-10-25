package com.baeldung.hexagonal;

import com.baeldung.hexagonal.domain.SweepstakeAdministration;
import com.baeldung.hexagonal.domain.SweepstakeService;
import com.baeldung.hexagonal.module.SweepstakeTestingModule;
import com.baeldung.hexagonal.sampledata.SampleData;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Mainapp {
	public static void main(String[] args) {

		
	    Injector injector = Guice.createInjector(new SweepstakeTestingModule());
	   // MongoConnectionPropertiesLoader.load();
	    // start new lottery round
	    SweepstakeAdministration administration = injector.getInstance(SweepstakeAdministration.class);
	    administration.resetSweepstake();
	    
	    // submit some lottery tickets
	    SweepstakeService service = injector.getInstance(SweepstakeService.class);
	    SampleData.submitTickets(service, 20);
	    
	    // perform sweepstake
	    administration.performSweepstake();
	  }
}
