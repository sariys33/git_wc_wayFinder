package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Reviews;
import lombok.Data;

@Data
public class ReviewForm {

    private Long id;
    private Long place_id;
    private Long user_id;
    private String writer;
    private String content;

    // ReviewForm -> Reviews(Entity)
    public Reviews toEntity(){
        Reviews review = new Reviews();
        review.setId(this.id);
        review.setPlace_id(this.place_id);
        review.setUser_id(this.user_id);
        review.setWriter(this.writer);
        review.setContent(this.content);
        return review;
    }

}
