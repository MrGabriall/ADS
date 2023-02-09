package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class CommentRecord {
    private Integer author;
    private String createdAt;
    private Integer pk;
    private String text;

}
