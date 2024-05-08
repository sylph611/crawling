package com.sylph.crawling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StockPriceService {

    @Autowired
    private StockPriceCrawler stockPriceCrawler;

    public String getCurrentPrice(String stockCode) {
        try {
            // 주가 정보 가져오기
            String currentPrice = stockPriceCrawler.getCurrentPrice(stockCode);
            return currentPrice;
        } catch (IOException e) {
            return "Error fetching current price information";
        }
    }

    public String getInvestorTrend(String stockCode) {
        try {
            // 투자자별 매매동향 정보 가져오기
            String investorTrend = stockPriceCrawler.crawlInvestorTrend(stockCode);
            return investorTrend;
        } catch (Exception e) {
            return "Error fetching investor trend information";
        }
    }
}