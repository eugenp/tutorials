package com.baeldung.hexagon;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = { ApplicationConfig.class })
public class BirthdayServiceIntegrationTest {

	@Autowired
	private BirthdayService birthdayService;


	@Test
	public void testWishHappyBirthday() {
		LocalDate testDate = LocalDate.of(0, 10, 27);
		birthdayService.wishHappyBirthday(testDate);
	}

}
