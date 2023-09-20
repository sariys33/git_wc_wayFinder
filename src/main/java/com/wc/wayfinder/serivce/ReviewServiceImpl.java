package com.wc.wayfinder.serivce;

import com.wc.wayfinder.domain.Places;
import com.wc.wayfinder.domain.Reviews;
import com.wc.wayfinder.domain.Users;
import com.wc.wayfinder.dto.ReviewForm;
import com.wc.wayfinder.dto.ReviewResponse;
import com.wc.wayfinder.repository.PlaceRepository;
import com.wc.wayfinder.repository.ReviewRepository;
import com.wc.wayfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;

    @Override
    public int add(ReviewForm form) {
        // 리뷰 작성할때 작성자는 id 값으로만 받음 (사용자 writer 작성 X)
        // user_id 로 사용자 정보 가져오기
        Users findUser = userRepository.findById(form.getUser_id());
        // form 에 작성자 넣어서 보내주기
        form.setWriter(findUser.getNickname());
        return reviewRepository.add(form.toEntity());
    }

    @Override
    public int modify(ReviewForm form) {
        return reviewRepository.modify(form.toEntity());
    }

    @Override
    public int remove(Long id) {
        return reviewRepository.remove(id);
    }

    @Override
    public ReviewResponse fetch(ReviewResponse review) {
        return new ReviewResponse(reviewRepository.fetch(review.toEntity()));
    }

    @Override
    public List<ReviewResponse> getList(Long place_id) {
        return reviewRepository.getList(place_id)
                .stream()
                .map(r -> new ReviewResponse(r))
                .collect(Collectors.toList());
    }

    @Override
    public ReviewResponse findRecentReviewByUserId(Long user_id) {
        Reviews findReview = reviewRepository.findRecentReviewByUserId(user_id);
        if(findReview != null) {
            ReviewResponse response = new ReviewResponse(findReview);
            Places findPlace = placeRepository.findById(findReview.getPlace_id());
            response.setToiletNm(findPlace.getToiletNm());
            response.setRoadAddress(findPlace.getRoadAddress());
            response.setLandAddress(findPlace.getLandAddress());
            return response;
        }
        return null;
    }

}
