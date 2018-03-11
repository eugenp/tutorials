package com.baeldung.dst;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

public class DaylightSavingTimeExamplesTest {

	@Test
	public void givenItalianTimeZone_WhenDSTHappens_ThenCorrectlyShiftTimeZone() throws ParseException {
		TimeZone tz = TimeZone.getTimeZone("Europe/Rome");
		Calendar cal = Calendar.getInstance(tz, Locale.ITALIAN);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ITALIAN);
		Date dateBeforeDST = df.parse("2018-03-25 01:55");
		prettyPrint(cal.getTimeZone());
		
		cal.setTime(dateBeforeDST);
		System.out.println("Before DST (00:55 UTC - 01:55 GMT+1) = " + dateBeforeDST);
		System.out.println("With this Calendar " + (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / (60 * 1000) + " minutes must be added to UTC (GMT TimeZone) to get a correct date for this TimeZone\n");
		assertThat(cal.get(Calendar.ZONE_OFFSET)==3600000);
		assertThat(cal.get(Calendar.DST_OFFSET)==0);

		cal.add(Calendar.MINUTE, 10);
		
		Date dateAfterDST = cal.getTime();
		System.out.println(" After DST (01:05 UTC - 03:05 GMT+2) = " + dateAfterDST);
		System.out.println("With this Calendar " + (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) / (60 * 1000) + " minutes must be added to UTC (GMT TimeZone) to get a correct date for this TimeZone\n");
		assertThat(cal.get(Calendar.DST_OFFSET)==3600000);		
		assertThat(dateAfterDST == df.parse("2018-03-25 03:05"));
		
		Long deltaBetweenDatesInMillis = dateAfterDST.getTime() - dateBeforeDST.getTime();
		Long seventyMinutesInMillis = (1000L * 60 * 70);
		assertThat(deltaBetweenDatesInMillis == seventyMinutesInMillis);
	}

	private void prettyPrint(TimeZone tz) {
		System.out.println(String.format(
				  "    Zone ID = %s (%s)\n"  
				+ "  RawOffset = %s minutes\n"
				+ "        DST = %s minutes\n"
				+ "  -----------------------------------------",
				tz.getID(), tz.getDisplayName(), tz.getRawOffset()/60000, tz.getDSTSavings()/60000));
	}
	
	@Test
	public void whenIterating_ThenPrintAllTimeZones() {
		for (String id : TimeZone.getAvailableIDs()) {
			TimeZone tz = TimeZone.getTimeZone(id);
			prettyPrint(tz);
		}
	}
}
