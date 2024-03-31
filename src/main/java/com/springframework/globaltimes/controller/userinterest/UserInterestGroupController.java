package com.springframework.globaltimes.controller.userinterest;

import com.springframework.globaltimes.dto.userinterest.UserInterestRequest;
import com.springframework.globaltimes.entity.userinterest.UserInterestGroup;
import com.springframework.globaltimes.service.userinterest.UserInterestGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1/user-interest")
@RequiredArgsConstructor
@Tag(name = "UserInterestGroup", description = "APIs for managing userInterestGroup data.")
public class UserInterestGroupController {

    private final UserInterestGroupService userInterestGroupService;

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get UserInterestGroup",
            description = "Retrieves a paginated list of UserInterestGroups associated with a given user ID.")
    public Page<Map<String, Object>> getUserInterestGroupByUserId(
            @RequestHeader String id,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return userInterestGroupService.getUserInterestGroupByUserId(id, page, size);
    }

    @GetMapping(value = "/data")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get UserInterestGroupData",
            description = "Fetches detailed data for a specific UserInterestGroup by its ID.")
    public Page<Map<String, Object>> getUserInterestGroupData(
            @RequestParam("id") String id,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return userInterestGroupService.getUserInterestGroupDataById(id, page, size);
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new UserInterestGroup",
            description = "Create a new UserInterestGroup with the given details.")
    public UserInterestGroup createUserInterestGroup(@RequestBody UserInterestRequest request){
        return userInterestGroupService.createUserInterestGroup(request);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing UserInterestGroup",
            description = "Update an existing UserInterestGroup with the given details.")
    public UserInterestGroup updateUserInterestGroup(@PathVariable String id, @RequestBody UserInterestRequest request){
        return userInterestGroupService.updateUserInterestGroup(id, request);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a UserInterestGroup",
            description = "Delete a UserInterestGroup by its ID.")
    public UserInterestGroup deleteUserInterestGroup(@PathVariable String id, @RequestHeader String userId ){
        return userInterestGroupService.deleteUserInterestGroup(id, userId);
    }
}
