package com.springframework.globaltimes.service.geography;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.entity.geography.Country;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.country.CountryRepository;
import com.springframework.globaltimes.repository.geography.country.CountrySpecifications;
import com.springframework.globaltimes.repository.geography.country.CountyByNameRepository;
import com.springframework.globaltimes.utils.PageableUtils;
import com.springframework.globaltimes.utils.StringFormatted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {

    public final CountryRepository countryRepository;
    public final CountrySpecifications countrySpecifications;
    public final CountyByNameRepository countyByNameRepository;

    public Country getCountryById(String id){
        log.info("Get country by id:{}",id);
        return countryRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Country")));
    }

    public Page<Country> getCountryWithSearch(String countryName, int page, int size ){
        var formattedCountryName = StringFormatted.slugToText(countryName);
        Specification<Country> spec = Specification.where(countrySpecifications.nameLike(formattedCountryName));
        var pageable = PageableUtils.createPageable(page, size);

        return countryRepository.findAll(spec, pageable);
    }

    public Page<Map<String, Object>> getCountryDataByCountryName(String countryName, int page, int size ){
        try {
            var formattedCountryName = StringFormatted.slugToText(countryName);
            var pageable = PageableUtils.createPageable(page, size);
            return countyByNameRepository.findCountyByName(formattedCountryName, pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get countries data by countryName"));
        }
    }

}
