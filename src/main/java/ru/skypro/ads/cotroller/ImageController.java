package ru.skypro.ads.cotroller;


import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/image")
public class ImageController {

    @PatchMapping("/{id}")
    public String updateAdsImage(@PathVariable String id){
        return "";
    }
}
