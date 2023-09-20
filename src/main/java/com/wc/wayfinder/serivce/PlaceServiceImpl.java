package com.wc.wayfinder.serivce;

import com.wc.wayfinder.domain.Places;
import com.wc.wayfinder.domain.Toilet;
import com.wc.wayfinder.domain.ToiletImg;
import com.wc.wayfinder.dto.*;
import com.wc.wayfinder.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final ImageUpload imageUpload;

    @Override
    public PlaceResponse findById(Long id) {
        Places findPlace = placeRepository.findById(id);
        if (findPlace != null) {
            findPlace.setImageFiles(placeRepository.findImagesByPid(id));
        }
        return new PlaceResponse(findPlace);
    }

    @Override
    public int update(Long place_id, PlaceForm form) throws IOException {
        int result = 0;
        form.setPlace_id(place_id);
        Places place = form.toEntity();
        placeRepository.updateName(form.toToilet());
        result = placeRepository.update(place);
        // 정보 갱신에 성공 했다면
        if(result == 1) {
            // 이미지 저장 요청
            List<ImageFile> imgList = imageUpload.uploadImages(form.getImageFiles());
            // 저장 요청한 이미지가 있다면
            if(!imgList.isEmpty()) {
                List<ToiletImg> images = new ArrayList<>();
                for (ImageFile img : imgList) {
                    // 이미지 파일 한개씩 꺼내서 이미지파일 + 고유번호로 만들기
                    ToiletImg image = img.toEntity();
                    image.setPlace_id(place_id);
                    images.add(image);
                }
                // 이미지 저장하기
                result = placeRepository.saveImages(images);
            }
        }
        return result;
    }

    @Override
    public int deleteImage(Long id) {
        return placeRepository.removeImage(id);
    }

    @Override
    public List<ToiletResponse> getToiletList() {
        return placeRepository.getToiletList().stream()
                .map(t -> new ToiletResponse(t))
                .collect(Collectors.toList());
    }

    @Override
    public SearchResponse searchByKeyword(PageInfo pageInfo) {
        return new SearchResponse(placeRepository.getResultCount(pageInfo.getKeyword()),
                placeRepository.searchByKeyword(pageInfo)
                        .stream().map(t -> new ToiletResponse(t))
                        .collect(Collectors.toList()));
    }

    @Override
    public int add(PlaceForm form) throws IOException {
        int result = placeRepository.savePlace(form.toEntity());
        // 정보 추가에 성공했다면
        if(result == 1) {
            // FK 이어 주기
            placeRepository.updateFK(form.getObject_id());
            // 이름 변경 했을시에 위치정보 테이블에도 이름 일치하게 바꿔주기
            placeRepository.updateName(form.toToilet());
            // 이미지도 저장 요청
            List<ImageFile> imgList = imageUpload.uploadImages(form.getImageFiles());
            Toilet findToilet = placeRepository.getInfoById(form.getObject_id());
            if(!imgList.isEmpty()) {
                List<ToiletImg> images = new ArrayList<>();
                for (ImageFile img : imgList) {
                    ToiletImg image = img.toEntity();
                    image.setPlace_id(findToilet.getPlace_id());
                    images.add(image);
                }
                placeRepository.saveImages(images);
            }
        }
        return result;
    }

    @Override
    public Long isExists(Long id) {
        return placeRepository.isExists(id);
    }

    @Override
    public ToiletResponse getInfoById(Long id) {
        return new ToiletResponse(placeRepository.getInfoById(id));
    }

    @Override
    public void updateFK(Long object_id) {
        placeRepository.updateFK(object_id);
    }

}
