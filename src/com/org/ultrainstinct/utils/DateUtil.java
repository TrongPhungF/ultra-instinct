/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.org.ultrainstinct.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
}
