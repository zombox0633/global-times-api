package com.springframework.globaltimes.controller.geography;

import com.springframework.globaltimes.service.geography.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/v1/city")
@RequiredArgsConstructor
@Tag(name = "City", description = "APIs for managing user data.")
public class CityController {

    private final CityService cityService;

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve City by ID",
            description = "Retrieves detailed information about a city based on the given city ID.")
    public Map<String,Object> getCityById(@PathVariable String id){ return cityService.getCityById(id);}

    @GetMapping(value = "/timezone")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "City",
            description = "Retrieves a list of cities that are in the specified timezone.")
    public List<Map<String, Object>> getCitiesByTimezoneId(
            @RequestParam("id") String id,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order
            ){
        return cityService.getCitiesByTimezoneId(id, page, size, sort, order);
    }
}
