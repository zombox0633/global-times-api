package com.springframework.globaltimes.controller.userinterest;

import com.springframework.globaltimes.dto.userinterest.UserInterestGroupCityRequest;
import com.springframework.globaltimes.dto.userinterest.UserInterestGroupCityRequests;
import com.springframework.globaltimes.entity.userinterest.UserInterestGroupCity;
import com.springframework.globaltimes.service.userinterest.UserInterestCityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/user-interest-city")
@RequiredArgsConstructor
@Tag(name = " UserInterestCity", description = "APIs for managing  userInterestCity data.")
public class UserInterestCityController {

    private final UserInterestCityService userInterestCityService;

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create UserInterestCity",
            description = "Creates a new UserInterestGroupCity instance with the specified group ID")
    public UserInterestGroupCity createUserInterestCity(@Valid @RequestBody UserInterestGroupCityRequest request){
        return userInterestCityService.createUserInterestCity(request);
    }

    @PostMapping(value = "/cities")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create multiple UserInterestGroupCities",
            description = "Creates multiple UserInterestGroupCity instances based on the provided list of requests.")
    public List<UserInterestGroupCity> createUserInterestCities (@Valid @RequestBody UserInterestGroupCityRequests requests){
        return userInterestCityService.createUserInterestCities(requests);
    }
}
