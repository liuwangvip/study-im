package com.isoler.studyim.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private DateUtil() {
    }

    public static final String DEFAULT_PATTERN = "yyyy-MM-dd";

    public static String format(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }


    /**
     * 将LocalDate转为Date
     * 光
     *
     * @param date LocalDate
     * @return日期
     */
    public static Date toDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将LocalDateTime.转为Date
     * 光
     * aparam dateTime LocalDateTime
     * areturn日期
     */
    public static Date toDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 将Date转为LocalDateTime
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDateTime toDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 将Date转为LocalDate
     *
     * @param date 日期
     * @return LocalDateTime
     */
    public static LocalDate toDate(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * toString
     *
     * @param localDate 日期
     * @return 日期
     */
    public static String toString(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * toString
     *
     * @param localDateTime 日期
     * @return 日期
     */
    public static String toString(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * toString
     *
     * @param date 日期
     * @return 日期
     */
    public static String toString(Date date) {
        return toString(date, DEFAULT_PATTERN);
    }

    /**
     * toString
     *
     * @param date    日期
     * @param pattern 模式
     * @return 日期
     */
    public static String toString(Date date, String pattern) {
        return toString(date, pattern, StringUtils.EMPTY);
    }

    /**
     * toString
     *
     * @param date     日期
     * @param pattern  模式
     * @param defValve 日期为空时的默认值
     * @return 日期
     */
    public static String toString(Date date, String pattern, String defValve) {
        return (date != null) ? toDateTime(date).format(DateTimeFormatter.ofPattern(pattern)) : defValve;
    }
}