package com.wc.wayfinder.repository;

import com.wc.wayfinder.domain.Places;
import com.wc.wayfinder.domain.Toilet;
import com.wc.wayfinder.domain.ToiletImg;
import com.wc.wayfinder.dto.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PlaceRepositoryImpl implements PlaceRepository {

    private final PlaceMapper placeMapper;

    @Override
    public Places findById(Long id) {
        return placeMapper.findById(id);
    }

    @Override
    public int saveImages(List<ToiletImg> images) {
        int result = 0;
        if(!images.isEmpty()){
            for (ToiletImg img : images) {
                result += placeMapper.saveImage(img);
            }
        }
        return result == images.size() ? 1 : 0;
    }

    @Override
    public int update(Places place) {
        return placeMapper.update(place);
    }

    @Override
    public int removeImage(Long id) {
        return placeMapper.removeImage(id);
    }

    @Override
    public List<Toilet> getToiletList() {
        return placeMapper.getToiletList();
    }

    @Override
    public List<Toilet> searchByKeyword(PageInfo pageInfo) {
        log.info("pageInfo : {}", pageInfo);
        return placeMapper.searchByKeyword(pageInfo);
    }

    @Override
    public int savePlace(Places place) {
        return placeMapper.savePlace(place);
    }

    @Override
    public Long isExists(Long id) {
        return placeMapper.isExists(id);
    }

    @Override
    public Toilet getInfoById(Long id) {
        return placeMapper.getInfoById(id);
    }

    @Override
    public void updateName(Toilet toilet) {
        placeMapper.updateName(toilet);
    }

    @Override
    public List<ToiletImg> findImagesByPid(Long id) {
        return placeMapper.findImagesByPid(id);
    }

    @Override
    public int getResultCount(String keyword) {
        return placeMapper.getResultCount(keyword);
    }

    @Override
    public void updateFK(Long object_id) {
        placeMapper.updateFK(object_id);
    }

}
