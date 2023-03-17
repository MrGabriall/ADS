package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for work with ads images
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageRecord {
    private String imageUrl;
    private Integer id;
}
