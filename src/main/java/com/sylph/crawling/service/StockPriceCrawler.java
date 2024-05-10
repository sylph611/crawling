package com.sylph.crawling.service;

import com.sylph.crawling.utils.StockEnum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getRecentNews(StockEnum stockEnum) throws IOException {
        // 카카오뱅크 관련 뉴스 검색 URL
        String searchUrl = "https://search.naver.com/search.naver?where=news&query="+stockEnum.getSearchKeyword()+"&sm=tab_opt&sort=1&photo=0&field=0&pd=0&ds=&de=&docid=&related=0&mynews=0&office_type=0&office_section_code=0&news_office_checked=&nso=so%3Add%2Cp%3Aall&is_sug_officeid=0&office_category=0&service_area=0";

        // HTML 파싱
        Document searchPageDoc = Jsoup.connect(searchUrl).get();

        // 최근 뉴스 목록 가져오기
        Elements newsElements = searchPageDoc.select("ul.list_news li");

        List<String> recentNews = new ArrayList<>();

        for (Element newsElement : newsElements) {
            if(!ObjectUtils.isEmpty(newsElement.selectFirst("a.news_tit"))) {
                String newsTitle = newsElement.selectFirst("a.news_tit").text();
                String newsTime = newsElement.selectFirst("span.info").text();

                recentNews.add(newsTitle+" / "+newsTime);
            }
        }

        return recentNews;
    }

    public String getPriceChangeFlag(StockEnum stockEnum) throws IOException {
        // 네이버 주식페이지 URL
        String url = "https://finance.naver.com/item/main.nhn?code="+stockEnum.getCode();

        // HTML 파싱
        Document doc = Jsoup.connect(url).get();

        // 현재 가격과 전일 종가 추출
        Element currentPriceElement = doc.selectFirst("p.no_today span.blind");
        Element prevCloseElement = doc.selectFirst("td.first span.blind");

        String priceChangeFlag = "";
        if (!ObjectUtils.isEmpty(currentPriceElement) && !ObjectUtils.isEmpty(prevCloseElement)) {
            String currentPriceStr = currentPriceElement.text().replace(",", ""); // 쉼표 제거
            String prevCloseStr = prevCloseElement.text().replace(",", ""); // 쉼표 제거

            // 문자열을 숫자로 변환
            double currentPrice = Double.parseDouble(currentPriceStr);
            double prevClose = Double.parseDouble(prevCloseStr);

            // 전일 대비 가격 변동 여부 확인
            if (currentPrice > prevClose) {
                priceChangeFlag = "<font color='red'>▲ +"+String.format("%.2f", currentPrice - prevClose)+"</font>"; // 상승
            } else if (currentPrice < prevClose) {
                priceChangeFlag = "<font color='blue'>▼ -"+String.format("%.2f", prevClose - currentPrice)+"</font>"; // 하락
            } else {
                priceChangeFlag = "<font color='black'>-</font>"; // 변동 없음
            }
        }

        return priceChangeFlag;
    }
}