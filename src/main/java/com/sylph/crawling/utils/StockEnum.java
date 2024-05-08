package com.sylph.crawling.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum StockEnum {

    NAVER("NAVER","035420"),
    KAKAOBANK("KAKAOBANK","323410");

    private final String name;
    private final String code;
}
