package com.sylph.crawling.dtos;

import lombok.Builder;
import lombok.Data;

@Data
public class StockInfo {
    private final String name;
    private final Integer price;
    private final String investorTrendInfo;

    @Builder
    private StockInfo(String name, Integer price, String investorTrendInfo) {
        this.name = name;
        this.price = price;
        this.investorTrendInfo = investorTrendInfo;
    }

    public static StockInfo from(String name, Integer price, String investorTrendInfo) {
        return StockInfo.builder()
                .name(name)
                .price(price)
                .investorTrendInfo(investorTrendInfo)
                .build();
    }
}
