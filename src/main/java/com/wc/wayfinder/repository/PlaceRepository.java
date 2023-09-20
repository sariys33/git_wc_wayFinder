package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Places;
import com.wc.wayfinder.domain.Toilet;
import com.wc.wayfinder.domain.ToiletImg;
import com.wc.wayfinder.dto.PageInfo;

import java.util.List;

public interface PlaceRepository {

    Places findById(Long id);

    // 이미지 정보 저장
    int saveImages(List<ToiletImg> images);

    // 화장실 정보 갱신
    int update(Places place);

    // 이미지 삭제
    int removeImage(Long id);

    // 화장실 위치정보 리스트 가져오기
    List<Toilet> getToiletList();

    // 화장실 키워드로 검색
    List<Toilet> searchByKeyword(PageInfo pageInfo);

    // 화장실 정보 저장
    int savePlace(Places place);

    // 화장실 정보 존재하는지 확인
    Long isExists(Long id);

    // 화장실 위치 정보 가져오기
    Toilet getInfoById(Long id);

    // 화장실 정보 추가할때 FK 연결해주기
    void updateFK(Long object_id);

    // 화장실 정보 추가/수정할때 바뀐 이름 수정해주기
    void updateName(Toilet toilet);

    // 화장실의 이미지 가져오기
    List<ToiletImg> findImagesByPid(Long id);

    // 키워드로 검색한 결과 목록 개수
    int getResultCount(String keyword);

}
