package refactored.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeFormatter {
    public static String getElapsedTime(LocalDateTime timestamp)
    {
        // Calculate time since posting
        String timeSincePosting;
        if (timestamp == null)
        {
            timeSincePosting = "Unknown";
        }
        LocalDateTime now = LocalDateTime.now();
        long seconds = timestamp.until(now, ChronoUnit.SECONDS);
        if (seconds < 60)
        {
            return seconds + "s";
        }
        long minutes = timestamp.until(now, ChronoUnit.MINUTES);
        if (minutes < 60)
        {
            return minutes + "m";
        }
        long hours = timestamp.until(now, ChronoUnit.HOURS);
        if (hours < 24)
        {
            return hours + "h";
        }
        long days = timestamp.until(now, ChronoUnit.DAYS);
        return days + "d";
    }
}
