package ru.skypro.ads.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AdsNotFoundException.class)
    public ResponseEntity<String> handleAdsNotFoundException(AdsNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ads not found");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AvatarNotFoundException.class)
    public ResponseEntity<String> handleAvatarNotFoundException(AvatarNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Avatar not found");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Image not found");
    }
}
