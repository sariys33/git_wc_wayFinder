package com.wc.wayfinder.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Toilet {

    private Long object_id; // PK
    private Long place_id;
    private String gu_nm; // 구 명
    private String hnr_nam; // 법적 동 명
    private Integer mtc_at; // 산지 여부
    private String masterno; // 주 지번
    private String slaveno; // 부 지번
    private String neadres_nm; // 새 주소 명
    private LocalDateTime creat_de; // 생성일
    private Double lat; // 위도
    private Double lng; // 경도
    
    private String fName; // 장소 이름
    private Double y_wgs84; // == 위도
    private Double x_wgs84; // == 경도

    // join 테이블 컬럼
    private String toiletNm;

}
