package com.wc.wayfinder.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Reviews {

    private Long id;
    private Long place_id;
    private Long user_id;
    private String writer;
    private String content;
    private Integer liked;
    private LocalDateTime createDate;

}
