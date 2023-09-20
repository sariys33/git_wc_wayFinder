package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Reviews;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    // 리뷰 저장
    int add(Reviews review);

    // 리뷰 수정
    int modify(Reviews review);

    // 리뷰 삭제
    int remove(Long id);

    // 해당 페이지의 내가 작성한 리뷰
    Reviews fetch(Reviews review);

    // 해당 페이지의 리뷰 리스트
    List<Reviews> getList(Long place_id);

    // 유저 고유번호로 최근 작성한 리뷰 가져오기
    Reviews findRecentReviewByUserId(Long user_id);

    // 작성자의 리뷰 모두 삭제 (회원 탈퇴 시)
    void removeAllAsWriter(Long user_id);

}
