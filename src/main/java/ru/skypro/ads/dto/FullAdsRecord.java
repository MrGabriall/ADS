package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class FullAdsRecord {
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer pk;
    private Integer price;
    private String title;
}
