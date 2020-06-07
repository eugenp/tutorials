package com.baeldung.clock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Clock;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClockUnitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClockUnitTest.class);

	@Test
	public void givenClock_withSytemUTC_retrievesInstant() {
		
		Clock clockUTC = Clock.systemUTC();

		assertEquals(clockUTC.getZone(), ZoneOffset.UTC);
		assertEquals(clockUTC.instant().equals(null), false);

		LOGGER.debug("UTC instant :: " + clockUTC.instant());
	}

	@Test
	public void givenClock_withSytem_retrievesInstant() {

		Clock clockSystem = Clock.system(ZoneId.of("Asia/Calcutta"));

		assertEquals(clockSystem.getZone(), ZoneId.of("Asia/Calcutta"));
		assertEquals(clockSystem.instant().equals(null), false);

		LOGGER.debug("System zone :: " + clockSystem.getZone());
	}

	@Test
	public void givenClock_withSytemDefaultZone_retrievesInstant() {
		
		Clock clockSystemDefault = Clock.systemDefaultZone();

		assertEquals(clockSystemDefault.getZone().equals(null), false);
		assertEquals(clockSystemDefault.instant().equals(null), false);

		LOGGER.debug("System Default instant :: " + clockSystemDefault.instant());
	}

	@Test
	public void givenClock_withSytemUTC_retrievesTimeInMillis() {
		
		Clock clockMillis = Clock.systemDefaultZone();

		assertEquals(clockMillis.instant().equals(null), false);
		assertTrue(clockMillis.millis() > 0);

		LOGGER.debug("System Default millis :: " + clockMillis.millis());
	}

	@Test
	public void givenClock_usingOffset_retrievesFutureDate() {
		
		Clock baseClock = Clock.systemDefaultZone();

		// result clock will be later than baseClock
		Clock futureClock = Clock.offset(baseClock, Duration.ofHours(72));

		assertEquals(futureClock.instant().equals(null), false);
		assertTrue(futureClock.millis() > baseClock.millis());

		LOGGER.debug("Future Clock instant :: " + futureClock.instant());
	}

	@Test
	public void givenClock_usingOffset_retrievesPastDate() {
		Clock baseClock = Clock.systemDefaultZone();

		// result clock will be later than baseClock
		Clock pastClock = Clock.offset(baseClock, Duration.ofHours(-72));

		assertEquals(pastClock.instant().equals(null), false);
		assertTrue(pastClock.millis() < baseClock.millis());

		LOGGER.debug("Past Clock instant :: " + pastClock.instant());
	}

	@Test
	public void givenClock_usingTick_retrievesInstant() {
		Clock clockDefaultZone = Clock.systemDefaultZone();
		Clock clocktick = Clock.tick(clockDefaultZone, Duration.ofSeconds(300));

		assertEquals(clockDefaultZone.instant().equals(null), false);
		assertEquals(clocktick.instant().equals(null), false);
		assertTrue(clockDefaultZone.millis() > clocktick.millis());

		LOGGER.debug("Clock Default Zone instant : " + clockDefaultZone.instant());
		LOGGER.debug("Clock tick instant: " + clocktick.instant());
	}

	@Test(expected=IllegalArgumentException.class)
	public void givenClock_usingTickDurationNegative_throwsException() {
		
		Clock clockDefaultZone = Clock.systemDefaultZone();
		Clock.tick(clockDefaultZone, Duration.ofSeconds(-300));

	}
	
	@Test
	public void givenClock_usingTickSeconds_retrievesInstant() {
		ZoneId zoneId = ZoneId.of("Asia/Calcutta");
		Clock tickSeconds = Clock.tickSeconds(zoneId);

		assertEquals(tickSeconds.instant().equals(null), false);
		LOGGER.debug("Clock tick seconds instant :: " + tickSeconds.instant());

		tickSeconds = Clock.tick(Clock.system(ZoneId.of("Asia/Calcutta")), Duration.ofSeconds(100));
		assertEquals(tickSeconds.instant().equals(null), false);
	}

	@Test
	public void givenClock_usingTickMinutes_retrievesInstant() {
		
		Clock tickMinutes = Clock.tickMinutes(ZoneId.of("Asia/Calcutta"));

		assertEquals(tickMinutes.instant().equals(null), false);
		LOGGER.debug("Clock tick seconds instant :: " + tickMinutes.instant());

		tickMinutes = Clock.tick(Clock.system(ZoneId.of("Asia/Calcutta")), Duration.ofMinutes(5));
		assertEquals(tickMinutes.instant().equals(null), false);
	}

	@Test
	public void givenClock_usingWithZone_retrievesInstant() {
		
		ZoneId zoneSingapore = ZoneId.of("Asia/Singapore");
		Clock clockSingapore = Clock.system(zoneSingapore);

		assertEquals(clockSingapore.instant().equals(null), false);
		LOGGER.debug("clockSingapore instant : " + clockSingapore.instant());

		ZoneId zoneCalcutta = ZoneId.of("Asia/Calcutta");
		Clock clockCalcutta = clockSingapore.withZone(zoneCalcutta);
		assertEquals(clockCalcutta.instant().equals(null), false);
		LOGGER.debug("clockCalcutta instant : " + clockSingapore.instant());
	}

	@Test
	public void givenClock_usingGetZone_retrievesZoneId() {
		
		Clock clockDefaultZone = Clock.systemDefaultZone();
		ZoneId zone = clockDefaultZone.getZone();

		assertEquals(zone.getId().equals(null), false);
		LOGGER.debug("Default zone instant : " + clockDefaultZone.instant());
	}
}
