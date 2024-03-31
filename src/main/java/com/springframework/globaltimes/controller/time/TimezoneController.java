package com.springframework.globaltimes.controller.time;


import com.springframework.globaltimes.dto.time.CreateTimezoneRequest;
import com.springframework.globaltimes.entity.time.Timezone;
import com.springframework.globaltimes.service.time.TimezoneService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/v1/timezone")
@RequiredArgsConstructor
@Tag(name = "Timezone", description = "APIs for managing timezone data.")
public class TimezoneController {

    private final TimezoneService timezoneService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Timezone",
            description = "Retrieves timezone details by its ID.")
    public Timezone getTimezoneById(@PathVariable("id") String id){return timezoneService.getTimezoneById(id); }

    @GetMapping(value = "country/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Timezone by Country ID",
            description = "Retrieves timezone details by the ID of the country it is associated with.")
    public Timezone getTimezoneByCountryId(@PathVariable("id") String id){return timezoneService.getTimezoneByCountryId(id);}


    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Timezone",
            description = "Creates a new timezone with the given details")
    public Timezone createTimezone(@Valid @RequestBody CreateTimezoneRequest request){return timezoneService.createTimezone(request);}
}
