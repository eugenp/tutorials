package com.baeldung.timezonedisplay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimezoneDisplayJava7 {

    public enum OffsetBase {
        GMT, UTC
    }

    public List<String> getTimeZoneList(TimezoneDisplayJava7.OffsetBase base) {
        String[] availableZoneIds = TimeZone.getAvailableIDs();
        List<String> result = new ArrayList<>(availableZoneIds.length);

        for (String zoneId : availableZoneIds) {
            TimeZone curTimeZone = TimeZone.getTimeZone(zoneId);

            String offset = calculateOffset(curTimeZone.getRawOffset());

            result.add(String.format("(%s%s) %s", base, offset, zoneId));
        }

        Collections.sort(result);

        return result;
    }

    private String calculateOffset(int rawOffset) {
        if (rawOffset == 0) {
            return "+00:00";
        }
        long hours = TimeUnit.MILLISECONDS.toHours(rawOffset);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(rawOffset);
        minutes = Math.abs(minutes - TimeUnit.HOURS.toMinutes(hours));

        return String.format("%+03d:%02d", hours, Math.abs(minutes));
    }

}
