package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for work with avatars
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvatarRecord {
    private Integer id;
    private String avatarUrl;
}
