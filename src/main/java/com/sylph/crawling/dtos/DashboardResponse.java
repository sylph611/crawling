package com.sylph.crawling.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class DashboardResponse {

    private LocalDateTime time;
    private List<StockInfo> stockInfoList;

    @Builder
    public DashboardResponse(LocalDateTime time, List<StockInfo> stockInfoList) {
        this.time = time;
        this.stockInfoList = stockInfoList;
    }

    public static DashboardResponse of(List<StockInfo> list, LocalDateTime time) {
        return DashboardResponse.builder()
                .stockInfoList(list)
                .time(time)
                .build();
    }
}
