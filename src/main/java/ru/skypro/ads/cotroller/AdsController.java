package ru.skypro.ads.cotroller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.dto.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("ads")
public class AdsController {

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperAds getAds() {
        return new ResponseWrapperAds();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public AdsRecord addAds(@RequestBody CreateAdsReq adsReq, @RequestBody MultipartFile multipartFile) {
        return new AdsRecord();
    }

    @GetMapping(value = "/{ad_pk}/comments",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperComment getComments(@PathVariable("ad_pk") Integer adPk) {
        return new ResponseWrapperComment();
    }

    @PostMapping(value = "/{ad_pk}/comments",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord addComments(@PathVariable("ad_pk") Integer adPk, @RequestBody CommentRecord commentRecord) {
        return new CommentRecord();
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public FullAdsRecord getFullAds(@PathVariable Integer id) {
        return new FullAdsRecord();
    }

    @DeleteMapping("/{id}")
    public void deleteAds(@PathVariable Integer id) {
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AdsRecord updateAds(@PathVariable Integer id, @RequestBody CreateAdsReq createAdsReq) {
        return new AdsRecord();
    }

    @GetMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord getComments(@PathVariable("ad_pk") Integer adPk, @PathVariable Integer id) {
        return new CommentRecord();
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public void deleteComments(@PathVariable("ad_pk") Integer adPk, @PathVariable Integer id) {
    }

    @PatchMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord updateComments(@PathVariable("ad_pk") Integer adPk, @PathVariable Integer id, @RequestBody CommentRecord commentRecord) {
        return new CommentRecord();
    }

    @GetMapping(value = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperAds getAdsMe(@RequestParam String userName) {
        return new ResponseWrapperAds();
    }
}
