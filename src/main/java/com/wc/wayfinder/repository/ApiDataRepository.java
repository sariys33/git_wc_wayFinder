package com.wc.wayfinder.repository;

import com.wc.wayfinder.dto.ApiDataDTO;

import java.util.List;

public interface ApiDataRepository {

    void saveApiData(List<ApiDataDTO> apiDataDTOList);

    void updateData(List<ApiDataDTO> apiDataDTOList);

}
