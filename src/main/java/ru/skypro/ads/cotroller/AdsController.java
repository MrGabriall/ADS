package ru.skypro.ads.cotroller;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.dto.*;
import ru.skypro.ads.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class AdsController {
    private final AdsService adsService;


    public AdsController(AdsService adsService) {
        this.adsService = adsService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseWrapperAds> getAds() {
        return ResponseEntity.ok(adsService.getAllAds());
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AdsRecord> addAds(@RequestPart(value = "properties") CreateAdsReq createAdsReq,
                                            @RequestPart("image") MultipartFile multipartFile,
                                            Authentication authentication) {
        return ResponseEntity.ok(adsService.addAds(createAdsReq, multipartFile, authentication.getName()));
    }

    @GetMapping(value = "/{ad_pk}/comments",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseWrapperComment> getComments(@PathVariable("ad_pk") Integer adPk) {
        return ResponseEntity.ok(adsService.getAllCommentsByAdsId(adPk));
    }

    @PostMapping(value = "/{ad_pk}/comments",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CommentRecord> addComments(@PathVariable("ad_pk") Integer adPk,
                                                     @RequestBody CommentRecord commentRecord,
                                                     Authentication authentication) {
        return ResponseEntity.ok(adsService.addComment(adPk, commentRecord, authentication.getName()));
    }


    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FullAdsRecord> getFullAds(@PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getFullAds(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAds(@PathVariable("id") Integer adsId,
                                          Authentication authentication) {
        adsService.deleteAds(adsId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<AdsRecord> updateAds(@PathVariable Integer id,
                                               @RequestBody CreateAdsReq createAdsReq,
                                               Authentication authentication) {
        return ResponseEntity.ok(adsService.updateAds(id, createAdsReq, authentication.getName()));
    }

    @GetMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CommentRecord> getComments(@PathVariable("ad_pk") Integer adPk,
                                                     @PathVariable Integer id) {
        return ResponseEntity.ok(adsService.getAdsComment(adPk, id));
    }

    @DeleteMapping("/{ad_pk}/comments/{id}")
    public ResponseEntity<Void> deleteComments(@PathVariable("ad_pk") Integer adPk,
                                               @PathVariable Integer id,
                                               Authentication authentication) {
        adsService.deleteComment(adPk, id, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(value = "/{ad_pk}/comments/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CommentRecord> updateComments(@PathVariable("ad_pk") Integer adPk,
                                                        @PathVariable Integer id,
                                                        @RequestBody CommentRecord commentRecord,
                                                        Authentication authentication) {
        return ResponseEntity.ok(adsService.updateComment(adPk, id, commentRecord, authentication.getName()));
    }


    @GetMapping(value = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseWrapperAds> getAdsMe(Authentication authentication) {
        ResponseWrapperAds adsMe = adsService.getAdsMe(authentication.getName());
        return ResponseEntity.ok(adsMe);
    }

    @PatchMapping(value = "/{id}/image",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> updateAdsImage(@PathVariable("id") Integer idAds,
                                                 @RequestPart("image") MultipartFile image,
                                                 Authentication authentication) {
        Pair<byte[], String> pair = adsService.updateAdsImage(idAds, image, authentication.getName());
        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }

    @GetMapping(value = "/{id}/image",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public ResponseEntity<byte[]> getAdsImage(@PathVariable("id") Integer imageId) {
        Pair<byte[], String> pair = adsService.getImageById(imageId);
        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }
}
