package com.wc.wayfinder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiDataDTO {

    // JSON 형식으로 데이터 받을때 사용
    @JsonProperty("OBJECTID")
    private Long object_id;
    @JsonProperty("GU_NM")
    private String gu_nm;
    @JsonProperty("HNR_NAM")
    private String hnr_nam;
    @JsonProperty("MTC_AT")
    private Integer mtc_at;
    @JsonProperty("MASTERNO")
    private String masterno;
    @JsonProperty("SLAVENO")
    private String slaveno;
    @JsonProperty("NEADRES_NM")
    private String neadres_nm;
    @JsonProperty("CREAT_DE")
    private LocalDateTime creat_de;
    @JsonProperty("LAT")
    private Double lat;
    @JsonProperty("LNG")
    private Double lng;
    // 추가될 데이터
    @JsonProperty("FNAME")
    private String fName;
    @JsonProperty("Y_WGS84")
    private Double y_wgs84;
    @JsonProperty("X_WGS84")
    private Double x_wgs84;

}
