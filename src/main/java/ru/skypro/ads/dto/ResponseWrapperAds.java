package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class ResponseWrapperAds {
    private Integer count;
    private AdsRecord[] results;
}
