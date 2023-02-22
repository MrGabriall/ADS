package ru.skypro.ads.cotroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("ads")
public class AdsController {
    private AdsService adsService;

    private AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperAds getAds() {
        return adsService.getAllAds();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public AdsRecord addAds(@RequestPart(value = "properties") CreateAdsReq createAdsReq,
                            @RequestPart("image") MultipartFile multipartFile) {
        return adsService.addAds(createAdsReq, multipartFile);
    }

    @GetMapping(value = "/{ad_pk}/comments",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperComment getComments(@PathVariable("ad_pk") Integer adPk) {
        return adsService.getAllCommentsById(adPk);
    }

    @PostMapping(value = "/{ad_pk}/comments",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord addComments(@PathVariable("ad_pk") Integer adPk,
                                     @RequestBody CommentRecord commentRecord) {
        return adsService.addComments(adPk, commentRecord);
    }

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public FullAdsRecord getFullAds(@PathVariable Integer id) {
        return adsService.getFullAds(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAds(@PathVariable Integer id) {
        adsService.deleteAds(id);
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AdsRecord updateAds(@PathVariable Integer id,
                               @RequestBody CreateAdsReq createAdsReq) {
        return adsService.updateAds(id, createAdsReq);
    }

//TODO I don't see necessity in this method. Or explain me how it might to work
    @GetMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord getComments(@PathVariable("ad_pk") Integer adPk,
                                     @PathVariable Integer id) {
        return adsService.getAdsComments(adPk, id);
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<?> deleteComments(@PathVariable("ad_pk") Integer adPk,
                                            @PathVariable Integer id) {
        boolean isDeleted = adsService.deleteComments(adPk, id);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CommentRecord> updateComments(@PathVariable("ad_pk") Integer adPk,
                                                        @PathVariable Integer id,
                                                        @RequestBody CommentRecord commentRecord) {
        CommentRecord commentRecord1 = adsService.updateComments(adPk, id, commentRecord);
        return new ResponseEntity<CommentRecord>(commentRecord1, HttpStatus.OK);
    }


    /*
    @GetMapping(value = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperAds getAdsMe() {
        return new ResponseWrapperAds();
    }

     */
}
