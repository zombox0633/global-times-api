package com.springframework.globaltimes.controller.geography;

import com.springframework.globaltimes.service.geography.ContinentService;
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
@RequestMapping(value = "/v1/continent")
@RequiredArgsConstructor
@Tag(name = "Continent", description = "APIs for managing continent data.")
public class ContinentController {
    private final ContinentService continentService;

    @GetMapping(value = "/name")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Retrieve Continent by Continent Name",
            description = "")
    public Page<Map<String, Object>> getContinentByName(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size){
        return  continentService.getContinentDataByName(name, page, size);
    }
}
