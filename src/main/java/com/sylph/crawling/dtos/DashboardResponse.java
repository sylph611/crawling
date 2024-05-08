package com.sylph.crawling.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class DashboardResponse {

    private List<StockInfo> stockInfoList;

    @Builder
    private DashboardResponse(List<StockInfo> stockInfoList) {
        this.stockInfoList = stockInfoList;
    }

    public static DashboardResponse of(List<StockInfo> list) {
        return DashboardResponse.builder()
                .stockInfoList(list)
                .build();
    }
}
