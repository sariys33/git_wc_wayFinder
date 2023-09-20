package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Reviews;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ReviewResponse {

    private Long id;
    private Long place_id;
    private Long user_id;
    private String writer;
    private String content;
    private Integer liked;
    private LocalDateTime createDate;

    // 조인 테이블
    private String toiletNm;
    private String roadAddress;
    private String landAddress;
    
    // Reviews(Entity) -> ReviewResponse
    public ReviewResponse(Reviews review) {
        this.id = review.getId();
        this.place_id = review.getPlace_id();
        this.user_id = review.getUser_id();
        this.writer = review.getWriter();
        this.content = review.getContent();
        this.liked = review.getLiked();
        this.createDate = review.getCreateDate();
    }

    // 내가 작성한 리뷰 가져올때 쓸 용도 (작성위치, 작성자만 있어도 충분)
    // ReviewResponse -> Reviews(Entity)
    public Reviews toEntity() {
        Reviews review = new Reviews();
        review.setPlace_id(this.place_id);
        review.setUser_id(this.user_id);
        return review;
    }

}
