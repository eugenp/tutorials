package com.baeldung.springboot.init;

import com.baeldung.springboot.entity.Person;
import com.baeldung.springboot.respository.DBSqlExecutor;
import com.baeldung.springboot.respository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class H2DataInitializer implements ApplicationRunner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(H2DataInitializer.class);

	private PersonRepository personRepository;
	private DBSqlExecutor dbSqlExecutor;
	
	
	@Autowired
	public H2DataInitializer(PersonRepository personRepository, DBSqlExecutor dbSqlExecutor) {
		this.personRepository = personRepository;
		this.dbSqlExecutor = dbSqlExecutor;
	}
	
	@Override
	public void run(ApplicationArguments applicationArguments) throws Exception {
		
		LOGGER.info("H2DataInitializer:run");
		
		Person person = new Person();
		person.setFirstName("FirstJava");
		person.setLastName("LastJava");
		person.setEmail("RestAPI_integrationTest@code.com");
		person.setTotalRuns("28");
		person.setGender("M");
		person.setHomeRun("INDORE");
		person.setRunningClub("NA");
		person.setPostCode("CW2ZZZ");
		
		personRepository.save(person);
		
	}
	
}
