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
        String searchUrl = "https://search.naver.com/search.naver?where=news&query="+stockEnum.getSearchKeyword();

        // HTML 파싱
        Document searchPageDoc = Jsoup.connect(searchUrl).get();

        // 최근 뉴스 목록 가져오기
        Elements newsElements = searchPageDoc.select("ul.list_news li");

        // 현재 시간과 비교하기 위해 10분 전의 시간 구하기
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd. HH:mm");

        List<String> recentNews = new ArrayList<>();

        // 뉴스 목록에서 최근 10분 내에 게시된 기사 찾기
        for (Element newsElement : newsElements) {
            if(!ObjectUtils.isEmpty(newsElement.selectFirst("a.news_tit"))) {
                String newsTitle = newsElement.selectFirst("a.news_tit").text();
                String newsTime = newsElement.selectFirst("span.info").text();

                recentNews.add(newsTitle+" / "+newsTime);
            }
//            // 기사 시간을 LocalDateTime으로 변환하여 비교
//            LocalDateTime articleTime = LocalDateTime.parse(newsTime, formatter);
//
//            // 현재 시간과 비교하여 최근 10분 내에 게시된 기사인지 확인
//            if (articleTime.isAfter(tenMinutesAgo)) {
//                recentNews.add(newsTitle);
//            } else {
//                // 기사 목록이 최근 10분 이내에 게시된 것들이므로, 시간순으로 정렬되어 있으므로
//                // 더 이상 확인할 필요 없음
//                break;
//            }
        }

        return recentNews;
    }
}