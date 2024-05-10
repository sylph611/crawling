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

    /**
     * 대시보드 정보를 반환합니다.
     *
     * @return 대시보드 응답 객체
     */
    public DashboardResponse getDashboardInfo() {
        try {
            List<StockInfo> stockInfos = getStockInfos();
            return DashboardResponse.of(stockInfos, LocalDateTime.now());
        } catch (Exception e) {
            // 에러가 발생한 경우, 응답을 생성할 수 없음을 알리는 메시지를 포함한 런타임 예외를 던집니다.
            throw new RuntimeException("Failed to retrieve dashboard information.", e);
        }
    }

    /**
     * 모든 주식 정보를 가져옵니다.
     *
     * @return 주식 정보 목록
     * @throws RuntimeException 주식 정보를 가져오는 중에 예외가 발생한 경우
     */
    private List<StockInfo> getStockInfos() {
        List<StockInfo> stockInfoList = new ArrayList<>();
        try {
            // 모든 주식 정보를 가져와서 목록에 추가합니다.
            for (StockEnum stockEnum : StockEnum.values()) {
                String price = stockPriceCrawler.getCurrentPrice(stockEnum);
                String investorTrend = stockPriceCrawler.crawlInvestorTrend(stockEnum);
                List<String> news = stockPriceCrawler.getRecentNews(stockEnum);
                String priceChangeFlag = stockPriceCrawler.getPriceChangeFlag(stockEnum);

                int profitLoss = calculateProfitLoss(stockEnum.getAveragePrice(), stockEnum.getQuantity(), Integer.parseInt(price.replace(",", "")));

                // 주식 정보를 생성하고 목록에 추가합니다.
                stockInfoList.add(StockInfo.from(stockEnum.getName()
                        , Integer.parseInt(price.replace(",", ""))
                        , investorTrend
                        , news
                        , priceChangeFlag
                        , profitLoss)
                );
            }
        } catch (Exception e) {
            // 에러가 발생한 경우, 응답을 생성할 수 없음을 알리는 메시지를 포함한 런타임 예외를 던집니다.
            throw new RuntimeException("Failed to retrieve stock information.", e);
        }
        return stockInfoList;
    }

    private int calculateProfitLoss(int averagePrice, int quantity, int currentPrice) {
        return (currentPrice - averagePrice) * quantity;
    }
}