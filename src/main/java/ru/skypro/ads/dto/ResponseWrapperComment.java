package ru.skypro.ads.dto;

import lombok.Data;

@Data
public class ResponseWrapperComment {
    private Integer count;
    private CommentRecord[] results;
}
