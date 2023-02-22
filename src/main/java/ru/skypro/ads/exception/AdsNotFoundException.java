package ru.skypro.ads.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdsNotFoundException extends RuntimeException {

    public AdsNotFoundException(String message) {
        super(message);
    }

    public AdsNotFoundException(Integer id) {
        super("Ads" + id + " not found");
    }

    public AdsNotFoundException() {
        super("Ads not found");
    }
}
