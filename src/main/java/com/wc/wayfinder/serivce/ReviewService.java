package com.wc.wayfinder.serivce;

import com.wc.wayfinder.dto.ReviewForm;
import com.wc.wayfinder.dto.ReviewResponse;

import java.util.List;

public interface ReviewService {

    // 리뷰 저장
    int add(ReviewForm form);

    // 리뷰 수정
    int modify(ReviewForm form);

    // 리뷰 삭제
    int remove(Long id);

    // 해당 페이지의 내가 작성한 리뷰
    ReviewResponse fetch(ReviewResponse review);

    // 해당 페이지의 리뷰 리스트
    List<ReviewResponse> getList(Long placeId);

    // 유저 고유번호로 최근 작성한 리뷰 가져오기
    ReviewResponse findRecentReviewByUserId(Long user_id);

}
