package com.zy.website;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends DateUtils {

    private static Logger logger = LogManager.getLogger(DateUtil.class);

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HHMMSS_1 = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyyMMddHHmmssSSS";

    public static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat(YYYY_MM_DD);
    public static final SimpleDateFormat ISO_DATETIME_FORMAT = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);

    public static Date now() {
        return new Date();
    }

    /**
     * ISO date yyyy-MM-dd from a date value.
     *
     * @param date java.util.Date
     * @return
     */
    public static String toISODate(Date date) {
        return ISO_DATE_FORMAT.format(date);
    }

    /**
     * ISO datetime yyyy-MM-dd HH:mm:ss from a date value.
     *
     * @param date java.util.Date
     * @return
     */
    public static String toISODateTime(Date date) {
        return ISO_DATETIME_FORMAT.format(date);
    }

    /**
     * ISO datetime yyyy-MM-dd HH:mm:ss from a long value.
     *
     * @param seconds
     * @return
     */
    public static String toISODateTime(long seconds) {
        return ISO_DATETIME_FORMAT.format(new Date(seconds * 1000));
    }

    /**
     * 转换日期为指定格式的字符串形式
     *
     * @param date    java.util.Date
     * @param pattern java.lang.String
     * @return
     */
    public static String format(Date date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param seconds
     * @param pattern java.lang.String
     * @return
     */
    public static String format(long seconds, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = sdf.format(new Date(seconds * 1000));
        return str;
    }

    /**
     * @param dateString java.lang.String
     * @param pattern    java.lang.String
     * @return
     */
    public static Date parseDate(String dateString, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 把指定日期对象设置为 0时0分0秒；如：2019-03-12 00:00:00
     *
     * @param date java.util.Date
     * @return
     */
    public static Date toMidnight(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return calendar.getTime();
    }

    /**
     * 把指定日期对象设置为 23时59分59秒；如：2019-03-12 23:59:59
     *
     * @param date java.util.Date
     * @return
     */
    public static Date toLastSecondOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        return calendar.getTime();
    }

    /**
     * 获得两个日期相差天数
     *
     * @param begin java.util.Date
     * @param end   java.util.Date
     * @return
     */
    public static int intervalDays(Date begin, Date end) {
        Date cBegin = DateUtil.toMidnight(DateUtil.clone(begin));
        Date cEnd = DateUtil.toLastSecondOfDay(DateUtil.clone(end));
        int days = Math.round((cEnd.getTime() / 1000 / 60 / 60 / 24 - cBegin.getTime() / 1000 / 60 / 60 / 24));
        return Math.abs(days);
    }

    /**
     * 获得指定日期的当月1日
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfTheMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(DateUtil.clone(date));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得指定日期的当月最后一天
     *
     * @param date 指定日期
     * @return
     */
    public static Date getLastDayOfTheMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null)
            calendar.setTime(DateUtil.clone(date));
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * copy Date
     *
     * @param date java.util.Date
     * @return
     */
    public static Date clone(Date date) {
        return (Date) date.clone();
    }

    /**
     * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        String[] versionArray1 = version1.split("\\.");// 注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);// 取最小长度值
        while (idx < minLength) {
            try {
                int result = new Integer(versionArray1[idx]).compareTo(new Integer(versionArray2[idx]));
                if (result == 0) {
                    idx++;
                } else {
                    return result;
                }
            } catch (Exception e) {
                idx++;
            }
        }
        return new Integer(versionArray1.length).compareTo(new Integer(versionArray2.length));
    }

    /**
     * 将时间字符串转化为时间
     *
     * @param time 字符串时间
     * @return
     */
    public static Date transString2Date(String time) {
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = simpleDateFormat.parse(time);
        } catch (Exception e) {
            logger.error("时间转化发生异常", e);
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 判断当前时间是否在某时间段内;
     * -1 : 小于开始时间
     * 0  : 在开始时间和结束时间之内
     * 1  : 大于结束时间
     *
     * @param nowTime   当前时间
     * @param beginTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    public static int belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar nowDate = Calendar.getInstance();
        nowDate.setTime(nowTime);

        Calendar beginDate = Calendar.getInstance();
        beginDate.setTime(beginTime);

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(endTime);
        if (nowDate.before(beginDate)) {
            return -1;
        }
        if (nowDate.after(endDate)) {
            return 1;
        }
        return 0;
    }

}
