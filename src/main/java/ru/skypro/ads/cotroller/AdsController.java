package ru.skypro.ads.cotroller;

import org.springframework.http.MediaType;
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

//Done
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperAds getAds() {
        return adsService.getAllAds();
    }
//Done
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
                 consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public AdsRecord addAds(@RequestPart(value = "properties") CreateAdsReq createAdsReq,
                            @RequestPart("image") MultipartFile multipartFile) {
        return adsService.addAds(createAdsReq, multipartFile);
    }
//Done
    @GetMapping(value = "/{ad_pk}/comments",
                produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperComment getComments(@PathVariable("ad_pk") Integer adPk) {
        return adsService.getAllCommentsById(adPk);
    }
//Done
    @PostMapping(value = "/{ad_pk}/comments",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord addComments(@PathVariable("ad_pk") Integer adPk,
                                     @RequestBody CommentRecord commentRecord) {
        return adsService.addComments(adPk, commentRecord);
    }
//Done
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public FullAdsRecord getFullAds(@PathVariable Integer id) {
        return adsService.getFullAds(id);
    }
//Done
    @DeleteMapping("/{id}")
    public void deleteAds(@PathVariable Integer id) {
        adsService.deleteAds(id);
    }
//Done
    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AdsRecord updateAds(@PathVariable Integer id,
                               @RequestBody CreateAdsReq createAdsReq) {
        return adsService.updateAds(id, createAdsReq);
    }
//Done
//TODO I don't see necessity in this method. Or explain me how it might to work
    @GetMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord getComments(@PathVariable("ad_pk") Integer adPk,
                                     @PathVariable Integer id) {
        return adsService.getAdsComments(adPk, id);
    }
//Done
    @DeleteMapping("/{ad_pk}/comments/{id}")
    public void deleteComments(@PathVariable("ad_pk") Integer adPk,
                               @PathVariable Integer id) {
        adsService.deleteComments(adPk, id);
    }
//TODO
    @PatchMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public CommentRecord updateComments(@PathVariable("ad_pk") Integer adPk,
                                        @PathVariable Integer id,
                                        @RequestBody CommentRecord commentRecord) {
        return adsService.updateComments(adPk, id, commentRecord);
    }
//TODO I need input data. How can i take UserID?
    /*
    @GetMapping(value = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseWrapperAds getAdsMe() {
        return new ResponseWrapperAds();
    }

     */
}
