package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentRecord {
    private Integer author;
    private String createdAt;
    private Integer pk;
    private String text;
    private Integer adsId;
}
