package com.sylph.crawling.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class StockInfo {
    private final String name;
    private final Integer price;
    private final String investorTrendInfo;
    private final List<String> news;
    private final String priceChangeFlag;
    private final Integer profitLoss;

    @Builder
    public StockInfo(String name, Integer price, String investorTrendInfo, List<String> news, String priceChangeFlag, Integer profitLoss) {
        this.name = name;
        this.price = price;
        this.investorTrendInfo = investorTrendInfo;
        this.news = news;
        this.priceChangeFlag = priceChangeFlag;
        this.profitLoss = profitLoss;
    }

    public static StockInfo from(String name, Integer price, String investorTrendInfo, List<String> news, String priceChangeFlag, Integer profitLoss) {
        return StockInfo.builder()
                .name(name)
                .price(price)
                .investorTrendInfo(investorTrendInfo)
                .news(news)
                .priceChangeFlag(priceChangeFlag)
                .profitLoss(profitLoss)
                .build();
    }
}
