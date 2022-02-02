package helper;

import java.time.LocalDateTime;

/**
 * LAMBDA interface will be utilized to convert LocalDateTime objects into strings in a format that is easier to read.
 */
public interface DateToStringFormat {
    String convertDateTime(LocalDateTime lclDT);
}
