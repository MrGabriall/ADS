package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class AdsRecord {
    private Integer pk;
    private String title;
    private Integer price;
    private String image;
    private Integer author;
}
