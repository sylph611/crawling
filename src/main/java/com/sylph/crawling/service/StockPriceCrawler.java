package com.sylph.crawling.service;

import com.sylph.crawling.utils.StockEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

@Service
public class StockPriceCrawler {

    public String getCurrentPrice(StockEnum stockEnum) throws IOException {
        // 네이버 주식페이지 URL
        String url = "https://finance.naver.com/item/main.nhn?code="+stockEnum.getCode();

        // HTML 파싱
        Document doc = Jsoup.connect(url).get();

        // 현재 주가 추출
        Element currentPriceElement = doc.selectFirst("p.no_today span.blind");

        String price = "";
        if(!ObjectUtils.isEmpty(currentPriceElement)) price = currentPriceElement.text();

        return price;
    }

    public String crawlInvestorTrend(StockEnum stockEnum) throws Exception {
        // 네이버 주식페이지 URL
        String url = "https://finance.naver.com/item/main.nhn?code="+stockEnum.getCode();

        // HTML 파싱
        Document doc = Jsoup.connect(url).get();

        // 투자자별 매매동향 데이터 추출
        Element investorTrendTable = doc.selectFirst("table.tb_type1");

        // "거개량"을 "거래량"으로 변경
        Elements thElements = investorTrendTable.select("th:contains(거개량)");

        String investorTrend = "";
        if(!ObjectUtils.isEmpty(thElements)) {
            for (Element thElement : thElements) {
                thElement.text("거래량");
            }
            investorTrend = investorTrendTable.outerHtml();
        }

        return investorTrend;
    }
}