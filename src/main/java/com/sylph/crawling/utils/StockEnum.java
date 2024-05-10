package com.sylph.crawling.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StockEnum {
    KAKAOBANK("KAKAOBANK","323410", "카카오뱅크", 28446, 1490),
    NAVER("NAVER","035420", "네이버", 213145, 72);


    private final String name;
    private final String code;
    private final String searchKeyword;
    private final Integer averagePrice;
    private final Integer quantity;
}
