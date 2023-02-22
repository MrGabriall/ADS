package ru.skypro.ads.cotroller;


import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.service.AdsService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/image")
public class ImageController {
    private AdsService adsService;

    public ImageController(AdsService adsService) {
        this.adsService = adsService;
    }

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE},
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<byte[]> updateAdsImage(@PathVariable("id") Integer idAds, @RequestPart("image") MultipartFile image) {
        Pair<byte[], String> pair = adsService.updateAdsImage(idAds, image);
        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }
}
