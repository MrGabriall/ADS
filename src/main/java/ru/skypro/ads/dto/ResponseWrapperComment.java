package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

/**
 * DTO for display list comments
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapperComment {
    @NonNull
    private Integer count;
    @NonNull
    private List<CommentRecord> results;
}
