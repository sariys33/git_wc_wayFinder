package com.wc.wayfinder.dto;

import lombok.Data;

@Data
public class PageInfo {

    private int pageNum; // 현재 페이지 번호
    private int listSize; // 한 페이지에 보여줄 갯수

    private String keyword; // 검색 키워드

    public PageInfo(int pageNum, int listSize, String keyword) {
        this.pageNum = pageNum;
        this.listSize = listSize;
        this.keyword = keyword;
    }

}
