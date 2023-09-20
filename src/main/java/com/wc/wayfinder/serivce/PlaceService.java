package com.wc.wayfinder.serivce;

import com.wc.wayfinder.dto.*;

import java.io.IOException;
import java.util.List;

public interface PlaceService {

    // 화장실 정보 한개 가져오기
    PlaceResponse findById(Long id);

    // 화장실 정보 갱신
    int update(Long place_id, PlaceForm form) throws IOException;

    // 이미지 삭제
    int deleteImage(Long id);

    // 화장실 정보 리스트
    List<ToiletResponse> getToiletList();

    // 화장실 키워드로 검색
    SearchResponse searchByKeyword(PageInfo pageInfo);

    // 화장실 정보 추가
    int add(PlaceForm form) throws IOException;

    // 화장실 정보 존재하는지 확인
    Long isExists(Long id);

    // 화장실 위치 정보 가져오기
    ToiletResponse getInfoById(Long id);

    // 키 수정
    void updateFK(Long object_id);

}
