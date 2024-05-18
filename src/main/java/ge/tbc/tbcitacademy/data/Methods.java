package ge.tbc.tbcitacademy.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class Methods {
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public static boolean isValidDate(String dateStr) {
        if (dateStr == null || !DATE_PATTERN.matcher(dateStr).matches()) {
            return false;
        }

        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
