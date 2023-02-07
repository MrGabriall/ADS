package ru.skypro.ads.cotroller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.ads.entity.User;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {
    /*
    private UserService userService;
    private UserController(UserService userService) {
        this.userService = userService;
    }
     */

    @Operation(
            summary = "set user password",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "set user password",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            })
    @PostMapping(path ="users/set_password")
    public ResponseEntity<?> setPassword() {
        return null;
    }

    @Operation(
            summary = "get user by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "get user by ID",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            })
    @GetMapping(path = "users/me/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        return null;
    }

    @Operation(
            summary = "update user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "update user",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            })
    @PatchMapping(path = "users/me")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        return null;
    }

    @Operation(
            summary = "update user image",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "update user image",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            })
    @PatchMapping(path = "users/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody String image) {
        return null;
    }

}
