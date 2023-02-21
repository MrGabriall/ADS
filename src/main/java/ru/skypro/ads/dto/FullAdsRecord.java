package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class FullAdsRecord {
    private String authorFirstName;
    private String authorLastName;
    private String email;
    private String phone;
    private String description;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;

    public FullAdsRecord(String authorFirstName, String authorLastName, String email, String phone, String description, String image, Integer pk, Integer price, String title) {
        this.authorFirstName = authorFirstName;
        this.authorLastName = authorLastName;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.image = image;
        this.pk = pk;
        this.price = price;
        this.title = title;
    }
}
