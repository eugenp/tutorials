package com.baeldung.clock;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;

public class ClockExample {

	public static void main(String[] args) {

		// gets systemUTC
		Clock clockUTC = Clock.systemUTC();
		System.out.println("UTC time :: " + clockUTC.instant());

		// using system()
		Clock clockSystem = Clock.system(ZoneId.of("Asia/Kolkata"));
		System.out.println(clockSystem.instant());

		// gets systemDefaultZone
		Clock clockSystemDefault = Clock.systemDefaultZone();
		System.out.println(clockSystemDefault);
		clockSystemDefault = Clock.system(ZoneId.systemDefault());
		System.out.println(clockSystemDefault);

		// gets instant from system default zone
		Clock clockInstant = Clock.systemDefaultZone();
		Instant instant = clockInstant.instant();
		System.out.println(instant);

		// gets millis
		Clock millis = Clock.systemDefaultZone();
		System.out.println(millis.millis());
		System.out.println(System.currentTimeMillis());

		// using offset

		Clock baseClock = Clock.systemDefaultZone();
		// result clock will be later than baseClock
		Clock clock5 = Clock.offset(baseClock, Duration.ofHours(72));
		System.out.println(clock5.instant());

		// result clock will be same as baseClock
		clock5 = Clock.offset(baseClock, Duration.ZERO);
		System.out.println(clock5.instant());

		// result clock will be earlier than baseClock
		clock5 = Clock.offset(baseClock, Duration.ofHours(-72));
		System.out.println(clock5.instant());

		// using tick
		Clock clockDefaultZone = Clock.systemDefaultZone();
		Clock clocktick = Clock.tick(clockDefaultZone, Duration.ofSeconds(30));
		System.out.println("Clock Default Zone : " + clockDefaultZone.instant());
		System.out.println("Clock tick : " + clocktick.instant());

		// using tickMinutes
		Clock tickMinutes = Clock.tickMinutes(ZoneId.of("Asia/Kolkata"));
		System.out.println(tickMinutes.instant());
		tickMinutes = Clock.tick(Clock.system(ZoneId.of("Asia/Kolkata")), Duration.ofMinutes(1));
		System.out.println(tickMinutes.instant());

		// using tickSeconds
		ZoneId zoneId = ZoneId.of("Asia/Calcutta");
		Clock tickSeconds = Clock.tickSeconds(zoneId);
		System.out.println(tickSeconds.instant());
		tickSeconds = Clock.tick(Clock.system(ZoneId.of("Asia/Kolkata")), Duration.ofSeconds(1));
		System.out.println(tickSeconds.instant());

		// using withZone
		ZoneId zone1 = ZoneId.of("Asia/Singapore");
		Clock clock11 = Clock.system(zone1);
		System.out.println(clock11.instant());

		ZoneId zone2 = ZoneId.of("Asia/Kolkata");
		Clock clock21 = clock11.withZone(zone2);
		System.out.println(clock21.instant());

		ZoneId zone = clockDefaultZone.getZone();
		System.out.println(zone.getId());

	}
}
