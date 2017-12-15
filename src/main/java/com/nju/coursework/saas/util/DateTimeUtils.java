package com.nju.coursework.saas.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;

public class DateTimeUtils {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static String date(LocalDate date) {
        return DATE_FORMATTER.format(date);
    }

    public static String time(LocalTime time) {
        return TIME_FORMATTER.format(time);
    }

    public static String date(LocalDateTime dateTime) {
        return date(dateTime.toLocalDate());
    }

    public static String time(LocalDateTime dateTime) {
        return time(dateTime.toLocalTime());
    }

    /**
     * 格式化日期。
     * 将日期格式化为{@code yyyy-MM-dd}。
     */
    public static String date(Instant instant) {
        return date(atDefault(instant));
    }

    /**
     * 格式化时间。
     * 将时间格式化为{@code HH:mm:ss}。
     */
    public static String time(Instant instant) {
        return time(atDefault(instant));
    }

    /**
     * 格式化日期时间。
     * 将其格式化为{@code yyyy-MM-dd HH:mm:ss}。
     */
    public static String dateTime(Instant instant) {
        return dateTime(atDefault(instant));
    }

    public static LocalDateTime atDefault(Instant instant) {
        return LocalDateTime.ofInstant(instant, zoneId());
    }

    /**
     * 系统内所有时间相关接口均基于该时区{@code ZoneId.of("Asia/Nanjing")}。
     */
    public static ZoneId zoneId() {
        return ZoneId.of("Asia/Shanghai");
    }

    public static String dateTime(LocalDateTime dateTime) {
        return DATE_TIME_FORMATTER.format(dateTime);
    }

    public static ZonedDateTime z(Instant instant) {
        return instant.atZone(zoneId());
    }

    /**
     * 计算从{@param from}到{@param to}度过多长时间，单位时间为{@param unit}。
     */
    public static int between(Instant from, Instant to, TemporalUnit unit) {
        return (int) z(from).until(z(to), unit);
    }
}
