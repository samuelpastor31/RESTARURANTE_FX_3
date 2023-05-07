package es.progcipfpbatoi.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConversion {

    public static String toString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return date.format(formatter);
    }

    public static LocalDateTime toLocalDateTime(String date, int hours, int minutes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(date + " " + String.format("%02d:%02d", hours, minutes), formatter);
    }
}
