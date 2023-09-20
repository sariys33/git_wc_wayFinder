package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Places;
import com.wc.wayfinder.domain.Toilet;
import com.wc.wayfinder.domain.ToiletImg;
import com.wc.wayfinder.dto.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlaceMapper {

    Places findById(Long id);

    // 이미지 저장 시키기
    int saveImage(ToiletImg img);

    // 화장실 정보 갱신
    int update(Places place);

    // 이미지 정보 가져오기
    List<ToiletImg> findImagesByPid(Long id);

    // 이미지 삭제 요청
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

    // 화장실 정보 추가/수정 할때 같이 수정해주기
    void updateName(Toilet toilet);

    // 화장실 정보 추가 할때 같이 수정해주기
    void updateFK(@Param("object_id") Long object_id);

    // 키워드로 검색한 결과 개수 가져오기
    int getResultCount(String keyword);
    
}
