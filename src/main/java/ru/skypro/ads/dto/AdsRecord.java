package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for work with ads
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdsRecord {
     private Integer pk;
     private String title;
     private Integer price;
     private String image;
     private Integer author;
}
