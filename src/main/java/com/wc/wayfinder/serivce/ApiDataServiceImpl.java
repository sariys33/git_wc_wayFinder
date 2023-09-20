package com.wc.wayfinder.serivce;

import com.wc.wayfinder.dto.ApiDataDTO;
import com.wc.wayfinder.repository.ApiDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiDataServiceImpl implements ApiDataService {

    private final ApiDataRepository apiDataRepository;

    @Override
    public void saveApiData(List<ApiDataDTO> apiDataDTOList) {
        apiDataRepository.saveApiData(apiDataDTOList);
    }

    @Override
    public void updateData(List<ApiDataDTO> apiDataDTOList) {
        apiDataRepository.updateData(apiDataDTOList);
    }

}
