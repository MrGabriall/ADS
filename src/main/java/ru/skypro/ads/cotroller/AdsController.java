package ru.skypro.ads.cotroller;

import org.springframework.web.bind.annotation.*;
import ru.skypro.ads.dto.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("ads")
public class AdsController {

    @GetMapping()
    public ResponseWrapperAds getAds() {
        return new ResponseWrapperAds();
    }

    @PostMapping()
    public AdsRecord addAds(AdsRecord adsRecord) {
        return new AdsRecord();
    }

    @GetMapping("/{ad_pk}/comments")
    public ResponseWrapperComment getComments(@PathVariable String ad_pk) {
        return new ResponseWrapperComment();
    }

    @PostMapping("/{ad_pk}/comments")
    public CommentRecord addComment(@PathVariable String ad_pk) {
        return new CommentRecord();
    }

    @GetMapping("/{id}")
    public FullAdsRecord getFullAds(@PathVariable String id) {
        return new FullAdsRecord();
    }

    @DeleteMapping("/{id}")
    public void deleteAds(@PathVariable String id) {
    }

    @PatchMapping("/{id}")
    public AdsRecord updateAds(@PathVariable String id) {
        return new AdsRecord();
    }

    @GetMapping("/{ad_pk}/comments/{id}")
    public CommentRecord getComment(@PathVariable String ad_pk, @PathVariable String id) {
        return new CommentRecord();
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public void deleteAds(@PathVariable String ad_pk, @PathVariable String id) {
    }

    @PatchMapping("/{ad_pk}/comments/{id}")
    public CommentRecord updateComment(@PathVariable String ad_pk, @PathVariable String id) {
        return new CommentRecord();
    }

    @GetMapping("/me")
    public AdsRecord getAdsMe() {
        return new AdsRecord();
    }
}
