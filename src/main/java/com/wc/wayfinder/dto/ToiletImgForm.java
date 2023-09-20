package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.ToiletImg;
import lombok.Data;

@Data
public class ToiletImgForm {

    private Long id;
    private Long place_id;
    private String orgFilename;

    // Form -> Entity
    public ToiletImg toEntity() {
        ToiletImg img = new ToiletImg();
        img.setId(this.id);
        img.setPlace_id(this.place_id);
        img.setOrgFilename(this.orgFilename);
        return img;
    }


}
