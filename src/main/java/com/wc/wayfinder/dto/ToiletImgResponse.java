package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.ToiletImg;
import lombok.Data;

@Data
public class ToiletImgResponse {

    private Long id;
    private Long place_id;
    private String orgFilename;
    private String filename;

    // Entity -> Response
    public ToiletImgResponse(ToiletImg img) {
        this.id = img.getId();
        this.place_id = img.getPlace_id();
        this.orgFilename = img.getOrgFilename();
        this.filename = img.getFilename();
    }

}
