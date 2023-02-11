package ru.skypro.ads.cotroller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/image")
public class ImageController {

    @PatchMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_OCTET_STREAM_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public Byte[] updateAdsImage(@PathVariable String id, @RequestPart("image") MultipartFile image) {
        return new Byte[0];
    }
}
