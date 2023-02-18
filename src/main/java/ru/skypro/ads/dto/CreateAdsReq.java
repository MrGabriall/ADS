package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class CreateAdsReq {
    private Integer id;
    private String description;
    private Integer price;
    private String title;
}
