package ru.skypro.ads.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.ads.entity.Ads;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapperAds {
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("results")
    private List<AdsRecord> results;

}
