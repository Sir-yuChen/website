package com.zy.website;

/**
 * @author Administrator
 **/
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateTimeUtil {

    public static void main(String[] args) {
        LocalDateTime ld = LocalDateTime.of(2020, Month.AUGUST, 23, 12, 44, 0);
        LocalDateTime cur = LocalDateTime.now();
        System.out.println(ld);
//        System.out.println(ld.toLocalDate());
        System.out.println(cur);
        long days = ChronoUnit.DAYS.between(ld, cur);
        System.out.println(days);
    }

    /**
     * Date -> LocalDateTime
     * @param date
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant() // 将Date转成时间戳的封装对象
                .atZone(ZoneId.systemDefault())  // 给时间戳对象加上当前时区信息，转成ZonedDateTime
                .toLocalDateTime(); // 将ZonedDateTime（复杂信息）转成LocalDatetime（简单信息）
    }

    /**
     * 计算两个LocalDateTime相差的天数
     * @param early
     * @param late
     * @return
     */
    public static long differSecondsBetween(LocalDateTime early, LocalDateTime late) {
        return ChronoUnit.SECONDS.between(early, late);
    }


}