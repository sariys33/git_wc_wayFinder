package com.wc.wayfinder.controller;

import com.wc.wayfinder.dto.ReviewForm;
import com.wc.wayfinder.dto.ReviewResponse;
import com.wc.wayfinder.serivce.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Slf4j
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 저장 요청
    @PostMapping("/add")
    public ResponseEntity<Void> addReview(@RequestBody ReviewForm form) {
        log.info("******** /add POST : addReview");
        log.info("ReviewForm : {}", form);
        int result = reviewService.add(form);
        return result == 1 ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 리뷰 수정 요청
    @PutMapping(value = "/{id}/modify", consumes = "application/json")
    public ResponseEntity<Void> modifyReview(@PathVariable Long id, @RequestBody ReviewForm form) {
        log.info("******** /{id}/modify PUT : modifyReview");
        log.info("id : {}", id);
        log.info("ReviewForm : {}", form);
        form.setId(id);
        int result = reviewService.modify(form);
        return result == 1 ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 리뷰 삭제 요청
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        log.info("******** /{id}/delete DELETE : deleteReview");
        log.info("id : {}", id);
        int result = reviewService.remove(id);
        return result == 1 ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 해당 페이지에 내가 작성한 리뷰
    @GetMapping("/{place_id}/{user_id}/fetch")
    public ResponseEntity<ReviewResponse> fetchReview(@PathVariable("place_id") Long place_id,
                                                      @PathVariable("user_id") Long user_id, ReviewResponse review) {
        log.info("******** /{place_id}/{user_id}/fetch GET : fetchReview");
        log.info("place_id : {}", place_id);
        log.info("user_id : {}", user_id);
        review.setPlace_id(place_id);
        review.setUser_id(user_id);
        // place_id, user_id 각각 보내기 불편하니 객체에 담아 보내기
        ReviewResponse findReview = reviewService.fetch(review);
        log.info("findReview : {}", findReview);
        return findReview != null ? new ResponseEntity<>(findReview, HttpStatus.OK)
                : null;
    }

    // 해당 페이지의 리뷰 리스트
    @GetMapping("/{place_id}/list")
    public ResponseEntity<List<ReviewResponse>> reviewList(@PathVariable Long place_id) {
        log.info("******** /{place_id}/list GET : reviewList");
        log.info("place_id : {}", place_id);
        List<ReviewResponse> reviewList = reviewService.getList(place_id);
        log.info("reviewList : {}", reviewList);
        return reviewList != null ? new ResponseEntity<>(reviewList, HttpStatus.OK)
                : null;
    }

    // 내가 작성한 최근 리뷰 요청
    @GetMapping("/recent/{user_id}")
    public ResponseEntity<ReviewResponse> recentReview(@PathVariable("user_id") Long user_id) {
        log.info("******** /recent/{user_id} GET");
        log.info("user_id : {}", user_id);
        // 유저 고유번호로 최근 작성한 리뷰 한개 가져오기
        ReviewResponse review = reviewService.findRecentReviewByUserId(user_id);
        return review != null ? new ResponseEntity<>(review, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
