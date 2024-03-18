package com.springframework.globaltimes.service.geography;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.entity.geography.Country;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.CountryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    public final CountryRepository countryRepository;

    public Country getCountryById(String id){
        log.info("Get country by id:{}",id);
        return countryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Country")));
    }
}
