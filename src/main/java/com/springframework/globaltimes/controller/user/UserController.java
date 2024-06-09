package com.springframework.globaltimes.controller.user;

import com.springframework.globaltimes.dto.user.UseRegisterRequest;
import com.springframework.globaltimes.dto.user.UserChangePasswordRequest;
import com.springframework.globaltimes.dto.user.UserRequest;
import com.springframework.globaltimes.dto.user.UserUpdateRequest;
import com.springframework.globaltimes.entity.user.User;
import com.springframework.globaltimes.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "APIs for managing user data, including retrieval, authentication, registration, and profile updates.")
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Customer Data by id",
            description = "Retrieves detailed information for a user based on their unique identifier. This endpoint is useful for obtaining the complete user profile data.")
    public User getUserById(@PathVariable("id") String id){ return  userService.getUserById(id);}

    @PostMapping(value = "/profile")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Profile User",
            description = "Registers a new user with the given details such as username, email, and password. Returns the registered user's information.")
    public User getRegister(@Valid @RequestBody UseRegisterRequest request){
        return userService.getUserProfile(request);
    }

    @PutMapping(value = "/profile/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update User Profile",
            description = "Updates the profile of an existing user based on the provided ID and returns the updated user's details.")
    public User updateUser(@Valid @PathVariable("id") String id, @RequestBody UserUpdateRequest request){
        return userService.updateProfile(id, request);
    }

    @PutMapping(value = "/password/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Change User Password",
            description = "Changes the password of an existing user based on the provided ID and the provided new password details.")
    public User updatePassword(@Valid @PathVariable("id") String id, @RequestBody UserChangePasswordRequest request){
        return userService.changePassword(id, request);
    }
}
