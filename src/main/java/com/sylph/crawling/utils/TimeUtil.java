package com.sylph.crawling.utils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtil {

    // 서울 시간을 반환하는 메서드
    public static String getSeoulTime() {
        ZoneId seoulZoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime now = LocalDateTime.now(seoulZoneId);
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
