package com.wc.wayfinder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResponse {
    
    private int listCount; // 전체 갯수
    private List<ToiletResponse> list; // 페이징 처리된 목록
    
}
