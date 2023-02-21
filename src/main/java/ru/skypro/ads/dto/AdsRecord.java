package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class AdsRecord {
    private Integer pk;
    private String title;
    private Integer price;
    private String image;
    private Integer author;

    public AdsRecord(Integer pk, String title, Integer price, String image, Integer author) {
        this.pk = pk;
        this.title = title;
        this.price = price;
        this.image = image;
        this.author = author;
    }
}
