package com.baeldung.cn1tutorial.util;

import com.codename1.l10n.L10NManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Bridges Java's modern date/time API and the older date/time types used by Codename One widgets.
 */
public final class DateTimeUtil {
    /**
     * Utility class; do not instantiate.
     */
    private DateTimeUtil() {
    }

    /**
     * Converts a {@link LocalDate} into a {@link Date} anchored to the current time zone.
     * <p>
     * CN1 formatters and some picker APIs still work with {@code java.util.Date}, hence the bridge.
     *
     * @param localDate date to convert
     * @return converted date, or {@code null}
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthValue() - 1);
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Converts a legacy {@link Date} into a {@link LocalDate} using the current default time zone.
     *
     * @param date legacy date value
     * @return converted local date, or {@code null}
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTime(date);
        return LocalDate.of(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    /**
     * Converts a {@link LocalTime} into the minute-based integer used by CN1 time pickers.
     *
     * @param localTime time to convert
     * @return minutes from midnight
     */
    public static int toPickerTime(LocalTime localTime) {
        if (localTime == null) {
            return 0;
        }
        return localTime.getHour() * 60 + localTime.getMinute();
    }

    /**
     * Converts a picker minute offset into a {@link LocalTime}.
     *
     * @param pickerTime minutes from midnight
     * @return converted local time
     */
    public static LocalTime fromPickerTime(int pickerTime) {
        int hours = pickerTime / 60;
        int minutes = pickerTime % 60;
        return LocalTime.of(hours, minutes);
    }

    /**
     * Formats a local date with the current device locale.
     *
     * @param localDate date to format
     * @return localized short date, or an empty string
     */
    public static String formatDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return L10NManager.getInstance().formatDateShortStyle(toDate(localDate));
    }

    /**
     * Formats a local time with the current device locale.
     *
     * @param localTime time to format
     * @return localized time, or an empty string
     */
    public static String formatTime(LocalTime localTime) {
        if (localTime == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, localTime.getHour());
        calendar.set(Calendar.MINUTE, localTime.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return L10NManager.getInstance().formatTime(calendar.getTime());
    }
}
