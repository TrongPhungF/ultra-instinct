/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author phung
 */
public class DateUtil {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String HH_MM_SS = "hh:mm:ss";

    public static String localDateToString(LocalDate locadate, String formatDate) {
        if (StringUtils.isBlank(formatDate) || ObjectUtils.isEmpty(locadate)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        return locadate.format(formatter);
    }

    public static String localDateTimeToString(LocalDateTime locaDateTime, String formatDate) {
        if (StringUtils.isBlank(formatDate) || ObjectUtils.isEmpty(locaDateTime)) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
        return locaDateTime.format(formatter);
    }

    public static Date getDateNow() {
        // Get the current locaNowl date and time
        LocalDateTime now = LocalDateTime.now();

        // Convert LocalDateTime to Instant
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();

        // Convert Instant to Date
        return Date.from(instant);
    }
}
