package com.sylph.crawling.controller;


import com.sylph.crawling.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockPriceController {

    @Autowired
    private StockPriceService stockPriceService;

    @GetMapping("/info")
    public String showStockPrices() {
        try {
            // 네이버 주식의 현재 주가 정보 조회
            String naverCurrentPrice = stockPriceService.getCurrentPrice("NAVER");
            // 카카오뱅크 주식의 현재 주가 정보 조회
            String kakaoBankCurrentPrice = stockPriceService.getCurrentPrice("KAKAOBANK");
            // 네이버 주식의 투자자별 매매동향 정보 조회
            String naverInvestorTrend = stockPriceService.getInvestorTrend("NAVER");
            // 카카오뱅크 주식의 투자자별 매매동향 정보 조회
            String kakaoBankInvestorTrend = stockPriceService.getInvestorTrend("KAKAOBANK");

            // HTML 파일 이름을 반환하여 화면에 표시
            return "네이버 현재 주가: <b>" + naverCurrentPrice + "</b><br>네이버 주식 투자자별 매매동향:<br><br>" + naverInvestorTrend + "<hr><br>" + "카카오뱅크 현재 주가: <b>" + kakaoBankCurrentPrice + "</b><br><br>카카오뱅크 주식 투자자별 매매동향:<br>" + kakaoBankInvestorTrend + "<hr>";
        } catch (Exception e) {
            // 에러 처리
            return "Error fetching stock information";
        }
    }
}