package ru.skypro.ads.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAds {
    private Integer count;
    private List<AdsRecord> results;
//пока не понял как работать с автогенирированными конструкторами
    public ResponseWrapperAds(Integer count, List<AdsRecord> results) {
        this.count = count;
        this.results = results;
    }

}
