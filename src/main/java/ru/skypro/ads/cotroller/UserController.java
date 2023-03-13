package ru.skypro.ads.cotroller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.ads.dto.UserRecord;
import ru.skypro.ads.dto.password.NewPassword;
import ru.skypro.ads.service.UserService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/set_password",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<NewPassword> setPassword(@RequestBody NewPassword newPassword,
                                                   Authentication authentication) {
        return new ResponseEntity<>(userService.setPassword(newPassword, authentication.getName()), HttpStatus.OK);
    }

    @GetMapping(path = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRecord> getUser(Authentication authentication) {
        return ResponseEntity.ok(userService.getUser(authentication.getName()));
    }

    @PatchMapping(path = "/me",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserRecord> updateUser(@RequestBody UserRecord userRecord,
                                                 Authentication authentication) {
        return new ResponseEntity<>(userService.updateUser(userRecord, authentication.getName()), HttpStatus.OK);
    }

    @PatchMapping(path = "/me/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Void> updateUserImage(@RequestPart("image") MultipartFile image,
                                                Authentication authentication) {
        userService.updateUserImage(image, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/me/get_avatar/{avatar_id}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable("avatar_id") Integer avatarId) {
        System.out.println("///////////// Response from GETIMAGE" );
        Pair<byte[], String> pair = userService.getAvatarById(avatarId);
        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }

}
