package ru.skypro.ads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapperAds {
    private Integer count;
    private List<AdsRecord> results;

}
