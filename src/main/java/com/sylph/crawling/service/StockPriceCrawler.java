package com.sylph.crawling.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StockPriceCrawler {

    public String getCurrentPrice(String stockCode) throws IOException {
        // 네이버 주식페이지 URL
        String url;
        if (stockCode.equalsIgnoreCase("NAVER")) {
            url = "https://finance.naver.com/item/main.nhn?code=035420";
        } else if (stockCode.equalsIgnoreCase("KAKAOBANK")) {
            url = "https://finance.naver.com/item/main.nhn?code=323410";
        } else {
            throw new IllegalArgumentException("Invalid stock code");
        }

        // HTML 파싱
        Document doc = Jsoup.connect(url).get();

        // 현재 주가 추출
        Element currentPriceElement = doc.selectFirst("p.no_today span.blind");
        String currentPrice = currentPriceElement.text();

        return currentPrice;
    }

    public String crawlInvestorTrend(String stockCode) throws Exception {
        // 네이버 주식페이지 URL
        String url;
        if (stockCode.equalsIgnoreCase("NAVER")) {
            url = "https://finance.naver.com/item/main.nhn?code=035420";
        } else if (stockCode.equalsIgnoreCase("KAKAOBANK")) {
            url = "https://finance.naver.com/item/main.nhn?code=323410";
        } else {
            throw new IllegalArgumentException("Invalid stock code");
        }

        // HTML 파싱
        Document doc = Jsoup.connect(url).get();

        // 투자자별 매매동향 데이터 추출
        Element investorTrendTable = doc.selectFirst("table.tb_type1");
        // "거개량"을 "거래량"으로 변경
        Elements thElements = investorTrendTable.select("th:contains(거개량)");
        for (Element thElement : thElements) {
            thElement.text("거래량");
        }
        String investorTrend = investorTrendTable.outerHtml();

        return investorTrend;
    }
}