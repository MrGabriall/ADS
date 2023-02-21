package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class CommentRecord {
    private Integer author;
    private String createdAt;
    private Integer pk;
    private String text;
    private Integer adsId;
    
    public CommentRecord(Integer pk, Integer author, Integer adsId, String text, String createdAt) {
        this.pk = pk;
        this.author = author;
        this.adsId = adsId;
        this.text = text;
        this.createdAt = createdAt;
    }
}
