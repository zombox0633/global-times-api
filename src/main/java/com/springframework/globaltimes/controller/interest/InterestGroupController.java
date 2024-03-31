package com.springframework.globaltimes.controller.interest;

import com.springframework.globaltimes.entity.interest.InterestGroup;
import com.springframework.globaltimes.service.interest.InterestGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "v1/interest-group")
@CrossOrigin(allowedHeaders = "*", origins = "*")
@RequiredArgsConstructor
@Tag(name = "InterestGroup", description = "")
public class InterestGroupController {

    private final InterestGroupService interestGroupService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve InterestGroup By Id",
            description = "")
    public InterestGroup getInterestGroupById(@PathVariable("id") String id){ return interestGroupService.getInterestGroupById(id);}

    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve InterestGroup",
            description = "")
    public Page<InterestGroup> getInterestGroup(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return interestGroupService.getInterestGroup(page,size);
    }

    @GetMapping(value = "/data")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve City by countryId",
            description = "")
    public Page<Map<String,Object>>getInterestGroupDataById(
            @RequestParam("id") String id,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        return interestGroupService.getInterestGroupDataById(id, page, size);
    }
}
