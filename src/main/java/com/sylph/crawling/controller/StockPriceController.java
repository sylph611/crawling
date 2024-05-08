package com.sylph.crawling.controller;


import com.sylph.crawling.dtos.DashboardResponse;
import com.sylph.crawling.service.StockPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class StockPriceController {

    private final StockPriceService stockPriceService;

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponse> getDashboard() {
        return ResponseEntity.ok(stockPriceService.getDashboardInfo());
    }

}