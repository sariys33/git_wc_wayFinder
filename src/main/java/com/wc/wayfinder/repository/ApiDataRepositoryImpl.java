package com.wc.wayfinder.repository;

import com.wc.wayfinder.dto.ApiDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ApiDataRepositoryImpl implements ApiDataRepository{

    private final ApiDataMapper apiDataMapper;

    @Override
    public void saveApiData(List<ApiDataDTO> apiDataDTOList) {
        for (ApiDataDTO apiData : apiDataDTOList) {
            try {
                apiDataMapper.saveApiData(apiData);
            } catch (DuplicateKeyException e) {
                // 중복 데이터를 무시하는 경우 처리할 내용 추가
                // 예: 이미 존재하는 데이터는 무시하고 로그를 남기거나 특별한 처리를 수행
                // 예를 들어 로그 출력:
                System.out.println("중복 데이터 무시: " + apiData.toString());
            }
        }
    }

    @Override
    public void updateData(List<ApiDataDTO> apiDataDTOList) {
        for(ApiDataDTO apiData : apiDataDTOList) {
            apiDataMapper.updateData(apiData);
        }
    }

}
