package com.wc.wayfinder.repository;

import com.wc.wayfinder.dto.ApiDataDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApiDataMapper {

    void saveApiData(ApiDataDTO apiDataDTO);

    void updateData(ApiDataDTO apiData);

}
