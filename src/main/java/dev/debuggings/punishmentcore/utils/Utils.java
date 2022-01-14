package dev.debuggings.punishmentcore.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static final Random r = new Random();
    private static final Pattern periodPattern = Pattern.compile("([0-9]+)([hdwmy])");

    public static String getRandomHexString(int numchars){
        StringBuilder sb = new StringBuilder();

        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }

        return sb.substring(0, numchars).toUpperCase();
    }

    public static Long parsePeriod(String period) {
        if (period == null) return null;

        period = period.toLowerCase();

        final Matcher matcher = periodPattern.matcher(period);
        Instant instant = Instant.EPOCH;

        while (matcher.find()) {
            final int num = Integer.parseInt(matcher.group(1));
            final String typ = matcher.group(2);
            final String s;

            switch ((s = typ).hashCode()) {
                case 100:
                    if (!s.equals("d")) {
                        continue;
                    }
                    instant = instant.plus(Duration.ofDays(num));
                    continue;
                case 104:
                    if (!s.equals("h")) {
                        continue;
                    }
                    instant = instant.plus(Duration.ofHours(num));
                    continue;
                case 109:
                    if (!s.equals("m")) {
                        continue;
                    }
                    instant = instant.plus(Duration.ofMinutes(num));
                    continue;
                default:
            }
        }
        return instant.toEpochMilli();
    }
}
