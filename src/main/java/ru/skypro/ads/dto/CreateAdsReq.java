package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for create ads requests
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateAdsReq {
    private String description;
    private Integer price;
    private String title;
}
