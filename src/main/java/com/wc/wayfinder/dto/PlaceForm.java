package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Places;
import com.wc.wayfinder.domain.Toilet;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@Slf4j
public class PlaceForm {

    private Long place_id;
    private String toiletNm;
    private String phone;
    private boolean refreshPaperDate;
    private boolean cleaningDate;
    private boolean diaperChangeTable;
    private String caution;
    private Long object_id;

    private Double lat;
    private Double lng;
    private String roadAddress;
    private String landAddress;

    // 개방시간 시:분 / 시:분 으로 받을 예정
    private int openHour;
    private int openMinute;
    private int closeHour;
    private int closeMinute;

    private List<MultipartFile> imageFiles; // 이미지 테이블에 따로 담을 예정

    // Form -> Places(Entity)
    public Places toEntity() {
        Places place = new Places();
        place.setId(this.place_id);
        place.setObject_id(this.object_id);
        place.setToiletNm(this.toiletNm);
        place.setPhone(this.phone);
        place.setRefreshPaperDate(getCurrentDate(this.refreshPaperDate));
        place.setCleaningDate(getCurrentDate(this.cleaningDate));
        place.setDiaperChangeTable(isChecked(this.diaperChangeTable));
        place.setCaution(this.caution);
        place.setOpeningTime(combineTimes(this.openHour, this.openMinute));
        place.setClosingTime(combineTimes(this.closeHour, this.closeMinute));
        place.setLat(this.lat);
        place.setLng(this.lng);
        place.setRoadAddress(this.roadAddress);
        place.setLandAddress(this.landAddress);
        return place;
    }

    // 시 + 분 합쳐서 리턴
    public LocalTime combineTimes(int hour, int minute) {
        return LocalTime.of(hour, minute);
    }

    // LocalDateTime 객체에 현재 시간 담기
    public LocalDateTime getCurrentDate(boolean result) {
        if(result) {
            return LocalDateTime.now();
        }
        return null;
    }

    // 기저귀 교환대 유무 체크 했다면 1, 아니라면 0
    public int isChecked(boolean result) {
        if(result) {
            return 1;
        }
        return 0;
    }

    // 수정 폼 내보낼때 기저귀 교환대 있다면 ture, 아니라면 false
    public boolean isExists(int result) {
        if(result == 1) {
            return true;
        }
        return false;
    }

    // Form -> Toilet(Entity)
    public Toilet toToilet() {
        Toilet toilet = new Toilet();
        toilet.setObject_id(this.getObject_id());
        toilet.setPlace_id(this.getPlace_id());
        toilet.setFName(this.getToiletNm());
        toilet.setLat(this.getLat());
        toilet.setLng(this.getLng());
        log.info("Toilet : {}", toilet);
        return toilet;
    }

}
