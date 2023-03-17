package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for display full ads info
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
}
