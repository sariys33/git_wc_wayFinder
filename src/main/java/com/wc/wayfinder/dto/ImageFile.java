package com.wc.wayfinder.dto;

import com.wc.wayfinder.domain.ToiletImg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFile {

    private String orgFilename;
    private String filename;

    // ImageFile -> Entity
    public ToiletImg toEntity() {
        ToiletImg img = new ToiletImg();
        img.setOrgFilename(this.orgFilename);
        img.setFilename(this.filename);
        return img;
    }

}
