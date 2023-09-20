package com.wc.wayfinder.serivce;

import com.wc.wayfinder.dto.ApiDataDTO;

import java.util.List;

public interface ApiDataService {


    // api 데이터 db에 저장
    void saveApiData(List<ApiDataDTO> apiDataDTOList);

    // api 데이터 추가
    void updateData(List<ApiDataDTO> apiDataDTOList);

}
