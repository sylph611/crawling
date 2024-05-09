package com.sylph.crawling.service;

import com.sylph.crawling.dtos.DashboardResponse;
import com.sylph.crawling.dtos.StockInfo;
import com.sylph.crawling.utils.StockEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockPriceService {

    private final StockPriceCrawler stockPriceCrawler;

    public DashboardResponse getDashboardInfo() {
        return DashboardResponse.of(getStockInfos(), LocalDateTime.now());
    }

    private List<StockInfo> getStockInfos() {
        List<StockInfo> list = new ArrayList<>();
        try {
            for (StockEnum stockEnum: StockEnum.values()) {
                String price = stockPriceCrawler.getCurrentPrice(stockEnum);
                String investorTrend = stockPriceCrawler.crawlInvestorTrend(stockEnum);

                list.add(StockInfo.from(stockEnum.getName(), price, investorTrend));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return list;
    }

}
