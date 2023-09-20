package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Reviews;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewMapper reviewMapper;

    @Override
    public int add(Reviews review) {
        return reviewMapper.add(review);
    }

    @Override
    public int modify(Reviews review) {
        return reviewMapper.modify(review);
    }

    @Override
    public int remove(Long id) {
        return reviewMapper.remove(id);
    }

    @Override
    public Reviews fetch(Reviews review) {
        return reviewMapper.fetch(review);
    }

    @Override
    public List<Reviews> getList(Long place_id) {
        log.info("place_id : {}", place_id);
        List<Reviews> reviewList = reviewMapper.getList(place_id);
        log.info("reviewList : {}", reviewList);
        return reviewList;
    }

    @Override
    public Reviews findRecentReviewByUserId(Long user_id) {
        return reviewMapper.findRecentReviewByUserId(user_id);
    }

    @Override
    public void removeAllAsWriter(Long user_id) {
        reviewMapper.removeAllAsWriter(user_id);
    }

}
