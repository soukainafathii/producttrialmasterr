package com.example.producttrialmaster.back.product.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static String formatTimestamp(Long timestamp) {
        if (timestamp == null) return null;
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        return dateTime.format(FORMATTER);
    }

    public static Long parseReadableDateToTimestamp(String readableDate) {
        if (readableDate == null) return null;
        LocalDateTime dateTime = LocalDateTime.parse(readableDate, FORMATTER);
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
