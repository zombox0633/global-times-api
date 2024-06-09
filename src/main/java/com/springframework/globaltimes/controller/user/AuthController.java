package com.springframework.globaltimes.controller.user;

import com.springframework.globaltimes.dto.user.UserRequest;
import com.springframework.globaltimes.entity.user.User;
import com.springframework.globaltimes.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "APIs for user authentication and registration.")
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/login")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Authenticate User",
            description = "Authenticates a user based on their credentials. Returns a session token or an authentication key upon successful authentication.")
    public ResponseEntity<Map<String, String>> postAuthenticate(@Valid @RequestBody UserRequest request){
        return userService.authenticateUser(request);
    }
    @PostMapping(value = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create User",
            description = "Creates a new user with the given details.")
    public User createUser(@Valid @RequestBody UserRequest request){
        return userService.createUser(request);
    }

}
