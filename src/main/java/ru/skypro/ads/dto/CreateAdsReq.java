package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class CreateAdsReq {
    private String description;
    private Integer price;
    private String title;
}
