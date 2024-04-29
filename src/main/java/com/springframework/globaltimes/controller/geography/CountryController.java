package com.springframework.globaltimes.controller.geography;

import com.springframework.globaltimes.entity.geography.Country;
import com.springframework.globaltimes.service.geography.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/v1/country")
@RequiredArgsConstructor
@Tag(name = "Country", description = "APIs for managing country data.")
public class CountryController {

    private final CountryService countryService;

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Country by Search Country Name",
            description = "")
    public Page<Country> getCountryDataWithSearch(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size){
        return countryService.getCountryWithSearch(name, page, size);
    }

    @GetMapping(value = "/name")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Country by Country Name",
            description = "")
    public Page<Map<String, Object>> getCountryDataByName(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size){
        return  countryService.getCountryDataByCountryName(name, page, size);
    }
}
