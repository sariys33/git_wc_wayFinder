package com.wc.wayfinder.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class Places {

    private Long id;
    private Long object_id;
    private String toiletNm;
    private String phone;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private LocalDateTime refreshPaperDate;
    private LocalDateTime cleaningDate;
    private int diaperChangeTable;
    private String caution;

    private Double lat;
    private Double lng;
    private String roadAddress;
    private String landAddress;

    private List<ToiletImg> imageFiles;

}
