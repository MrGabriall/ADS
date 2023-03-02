package ru.skypro.ads.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AdsNotFoundException.class)
    public ResponseEntity<String> handleAdsNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Ads not found");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AvatarNotFoundException.class)
    public ResponseEntity<String> handleAvatarNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Avatar not found");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handleImageNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Image not found");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handeCommentNotFoundException(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Comment not found");
    }
}
