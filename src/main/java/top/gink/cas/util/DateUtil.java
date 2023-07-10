package top.gink.cas.util;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    static final long dateMillis = 24 * 3600 * 1000L;
    private static final String[] dateFormats = new String[]{
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            "yyyy-MM-dd",
            "yyyy/MM/dd HH:mm:ss",
            "yyyy/MM/dd'T'HH:mm:ss'Z'",
            "yyyy/MM/dd",
            "yyyyMMddHHmmssSSS",
            "yyyyMMddHHmmss",
            "yyyyMMdd",
    };
    public static DateTimeFormatter DATE_FORMAT5 = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter DATE_FORMAT6 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter DATE_FORMAT8 = DateTimeFormatter.ofPattern(
            "yyyy/MM/dd HH:mm:ss");
    public static DateTimeFormatter DATE_FORMAT11 = DateTimeFormatter.ofPattern(
            "yyyyMMddHHmmssSSS");
    public static DateTimeFormatter DATE_FORMAT12 = DateTimeFormatter.ofPattern("yyyy/MM/dd");


    ///////////////////////////////format///////////////////////////////////////
    public static DateTimeFormatter DATE_FORMAT13 = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static String formatTime(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    /**
     * 格式化成yyyy-MM-dd HH:mm:ss;这样的
     *
     * @param time
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime time) {
        if (time == null) {
            return null;
        }
        return time.format(DATE_FORMAT5);
    }

    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMAT6);
    }

    /**
     * 格式化成"2014-02-28";这样的
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDate(date).format(DATE_FORMAT6);
    }

    public static String formatPureDateTime(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMAT11);
    }

    public static String formatPureDate(LocalDate date) {
        return date.format(DATE_FORMAT13);
    }

    public static String formatPureDate(Date date) {
        LocalDate localDate = toLocalDate(date);
        if (localDate == null) {
            return null;
        }
        return localDate.format(DATE_FORMAT13);
    }

    public static String formatDate(Date date, DateTimeFormatter formatter) {
        if (date == null) {
            return null;
        }
        return toLocalDate(date).format(formatter);
    }

    ///////////////////////////////build///////////////////////////////////////
    public static Timestamp buildTimeStamp(String str) {
        if (StringUtils.isNumeric(str)) {
            return new Timestamp(Long.parseLong(str));
        }
        Date date = buildDate(str);
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    public static java.sql.Date buildSqlDate(String str) {
        Date date = buildDate(str);
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    /**
     * 将字符串转化为date格式
     *
     * @param str
     * @return
     */
    public static Date buildDate(String str) {
        try {
            if (StringUtils.isBlank(str)) {
                return null;
            }
            return DateUtils.parseDate(str, dateFormats);
        } catch (ParseException e) {
            throw new RuntimeException(String.format("转换失败，输入的值：%s不是日期类型", str), e);
        }
    }

    public static LocalDateTime buildLocalDateTime(String localDateTime) {
        Date date = buildDate(localDateTime);
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date);
    }

    public static LocalDate buildLocalDate(String localDate) {
        Date date = buildDate(localDate);
        if (date == null) {
            return null;
        }
        return toLocalDate(date);
    }

    ///////////////////////////////to///////////////////////////////////////
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof java.sql.Date) {
            return LocalDateTime.of(((java.sql.Date) date).toLocalDate(), LocalTime.MIN);
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        if (date instanceof java.sql.Date) {
            return ((java.sql.Date) date).toLocalDate();
        }
        return toLocalDateTime(date).toLocalDate();
    }

    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    ///////////////////////////////other///////////////////////////////////////
    public static long diffDays(Date startDate, Date endDate) {
        return (DateUtils.truncate(endDate, Calendar.DATE).getTime() - DateUtils.truncate(startDate,
                Calendar.DATE).getTime()) / (1000L * 60 * 60 * 24);
    }

    ///////////////////////////////get///////////////////////////////////////
    public static Timestamp getCurTime() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    public static boolean isBeijingDateBegin(Date date) {
        long time = date.getTime();
        return (time + 8 * 3600 * 1000) == ((time + 8 * 3600 * 1000) / dateMillis) * dateMillis;
    }

}

