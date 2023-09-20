package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.Toilet;
import lombok.Data;

@Data
public class ToiletResponse {

    private Long object_id; // PK
    private Double lat; // 위도
    private Double lng; // 경도
    private String gu_nm; // 구
    private String hnr_nam; // 동
    private String fName; // 장소 이름

    private Long place_id; // 장소 상세 페이지 연결해줄 fk

    // Entity -> Response
    public ToiletResponse(Toilet toilet) {
        this.object_id = toilet.getObject_id();
        this.lat = toilet.getLat();
        this.lng = toilet.getLng();
        this.gu_nm = toilet.getGu_nm();
        this.hnr_nam = toilet.getHnr_nam();
        this.fName = toilet.getFName();
        this.place_id = toilet.getPlace_id();
    }

    // Response -> Entity
    public Toilet toEntity() {
        Toilet toilet = new Toilet();
        toilet.setObject_id(this.object_id);
        toilet.setLat(this.lat);
        toilet.setLng(this.lng);
        toilet.setGu_nm(this.gu_nm);
        toilet.setHnr_nam(this.hnr_nam);
        toilet.setFName(this.fName);
        toilet.setPlace_id(this.place_id);
        return toilet;
    }

}
