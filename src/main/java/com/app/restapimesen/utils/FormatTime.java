package com.app.restapimesen.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

public class FormatTime {
    public Timestamp formatTimeToTimestamp(String time) {
        LocalTime localTime = LocalTime.parse(time);

        LocalDate date = LocalDate.now();

        return Timestamp.valueOf(date.atTime(localTime));
    }
}
