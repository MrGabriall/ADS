package ru.skypro.ads.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.dto.UserRecord;
import ru.skypro.ads.dto.password.NewPassword;
import ru.skypro.ads.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path ="/set_password",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPassword) {
        return new ResponseEntity<>(userService.setPassword(newPassword), HttpStatus.OK);

    }

    @GetMapping(path = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRecord> getUser() {
        return new ResponseEntity<>(userService.getUser(), HttpStatus.OK);
    }

    @PatchMapping(path = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateUser(@RequestBody UserRecord userRecord) {
        userService.updateUser(userRecord);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/me/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateUserImage(@RequestPart("image") MultipartFile image) {
        userService.updateUserImage(image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
