package sharifplus.core.utils;

import java.util.Date;

/**
 * The util class for dates and times
 */
public class DateUtil {

    /**
     * Calculate human-readable different date with now. Ex. 1 day ago, 34 minute ago
     *
     * @param date The date
     * @return The human-readable different string. If date equals null, It's return Null string.
     */
    public static String diffWithNow(Date date) {
        // If date equal null then return null
        if (date == null) return "Null";

        // The current time
        Date now = new Date();

        // It's the time intervals in seconds. For example 1 minute equals 60 second and 1 day equals 86400 second
        long[] timeIntervals = new long[]{31536000, 2628000, 604800, 86400, 3600, 60, 1};
        String[] intervalNames = new String[]{"year", "month", "week", "day", "hour", "minute", "second"};

        // different in second
        long diff = (now.getTime() - date.getTime()) / 1000;
        int index = 0;
        // find the time unit with dived time different with timeIntervals.
        for (int i = 0; i < timeIntervals.length; i++) {
            if ((diff / timeIntervals[i]) >= 1) {
                index = i;
                break;
            }
        }

        // now dived time different with exact time unit interval then fill the largest (closest to positive infinity) value to n
        int n = (int) Math.floor(diff / timeIntervals[index]);

        // format the values as readable string
        return String.format("%s %s ago", n, intervalNames[index]);
    }
}
