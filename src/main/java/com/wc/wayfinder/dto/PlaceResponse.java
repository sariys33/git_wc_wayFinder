package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Places;
import com.wc.wayfinder.domain.ToiletImg;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
public class PlaceResponse {

    private Long id;
    private Long object_id;
    private String toiletNm;
    private String phone;
    private LocalDateTime refreshPaperDate;
    private LocalDateTime cleaningDate;
    private int diaperChangeTable;
    private String caution;

    private Double lat;
    private Double lng;
    private String roadAddress;
    private String landAddress;

    // 개방시간 시:분 / 시:분 따로 내보내기
    private int openHour;
    private int openMinute;
    private int closeHour;
    private int closeMinute;

    private List<ToiletImgResponse> imageFiles;

    // Places(Entity) -> PlaceResponse
    public PlaceResponse(Places place) {
        this.id = place.getId();
        this.object_id = place.getObject_id();
        this.toiletNm = place.getToiletNm();
        this.phone = place.getPhone();
        this.refreshPaperDate = place.getRefreshPaperDate();
        this.cleaningDate = place.getCleaningDate();
        this.diaperChangeTable = place.getDiaperChangeTable();
        this.caution = place.getCaution();
        if(place.getImageFiles() != null) {
            this.imageFiles = toImageResponse(place.getImageFiles());
        }
        this.openHour = extractHour(place.getOpeningTime());
        this.openMinute = extractMinute(place.getOpeningTime());
        this.closeHour = extractHour(place.getClosingTime());
        this.closeMinute = extractMinute(place.getClosingTime());
        this.lat = place.getLat();
        this.lng = place.getLng();
        this.roadAddress = place.getRoadAddress();
        this.landAddress = place.getLandAddress();
    }

    // List<Entity> -> List<Response> (이미지 파일들만)
    private List<ToiletImgResponse> toImageResponse(List<ToiletImg> imgList) {
        List<ToiletImgResponse> imageFiles = new ArrayList<>();
        if(!imgList.isEmpty()) {
            log.info("imgList : {}", imgList);
            for (ToiletImg img : imgList) {
                imageFiles.add(new ToiletImgResponse(img));
            }
        }
        log.info("imageFiles : {}", imageFiles);
        return imageFiles;
    }

    // 시간에서 Hour 만 추출
    public int extractHour(LocalTime time) {
        return time.getHour();
    }

    // 시간에서 Minute 만 추출
    public int extractMinute(LocalTime time) {
        return time.getMinute();
    }

    // LocalDateTime 원하는 형식으로 포맷팅
    public String formatLocalDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }


}
