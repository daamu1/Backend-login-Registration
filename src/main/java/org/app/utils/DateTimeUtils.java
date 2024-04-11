package org.app.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateTimeUtils {
    public static LocalDateTime epochToLocalDateTime(long epochTimeMillis) {
        long seconds = epochTimeMillis / 1000;
        Instant instant = Instant.ofEpochSecond(seconds);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
