package com.springframework.globaltimes.service.geography;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.entity.geography.City;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.city.CityRepository;
import com.springframework.globaltimes.service.time.TimezoneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {

    public final CityRepository cityRepository;

    public final TimezoneService timezoneService;
    public final ContinentService continentService;
    public final CountryService countryService;

    private static final String ID = "id";
    private static final String CITY_NAME = "name";
    private static final String COUNTRY_NAME = "country";
    private static final String CONTINENT_NAME = "continent";
    private static final String HIGHLIGHTED = "highlighted";
    private static final String TIMEZONE_NAME = "timezone";
    private static final String REGION_NAME = "region";

    //Get By ID
    public Map<String, Object> getCityById(String id){
        try {
            return cityRepository.findById(UUID.fromString(id)).map(city -> {
                Map<String, Object> cityInfo = emptyCityInfoMap();
                cityInfo.put(ID, city.getId().toString());
                cityInfo.put(CITY_NAME, city.getName());
                cityInfo.put(HIGHLIGHTED, city.getHighlighted());

                //Find timezone
                var timezone = timezoneService.getTimezoneById(city.getTimezoneId().toString());
                cityInfo.put(TIMEZONE_NAME, timezone.getTimezoneName());

                //Find country
                var country = countryService.getCountryById(timezone.getCountryId().toString());
                cityInfo.put(COUNTRY_NAME, country.getName());

                //Find continent
                var continent = continentService.getContinentById(country.getContinentId().toString());
                cityInfo.put(CONTINENT_NAME, continent.getName());
                cityInfo.put(REGION_NAME, continent.getRegionName());

                return cityInfo;
            }).orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("City")));
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get city by id"));
        }
    }

    public List<Map<String, Object>> getCitiesByTimezoneId(String timezoneId, int page, int size, String sort, String order){
        try {
            var pageable = createPageable(page, size, sort, order);

            Page<City> cities = cityRepository.findByTimezoneId(UUID.fromString(timezoneId), pageable);

            return cities.stream().map(city -> {
                Map<String, Object> cityInfo = emptyCityInfoMap();
                cityInfo.put(ID, city.getId().toString());
                cityInfo.put(CITY_NAME, city.getName());
                cityInfo.put(HIGHLIGHTED, city.getHighlighted());

                //Find timezone
                var timezone = timezoneService.getTimezoneById(city.getTimezoneId().toString());
                cityInfo.put(TIMEZONE_NAME, timezone.getTimezoneName());

                //Find country
                var country = countryService.getCountryById(timezone.getCountryId().toString());
                cityInfo.put(COUNTRY_NAME, country.getName());

                //Find continent
                var continent = continentService.getContinentById(country.getContinentId().toString());
                cityInfo.put(CONTINENT_NAME, continent.getName());
                cityInfo.put(REGION_NAME, continent.getRegionName());

                return cityInfo;
            }).toList();
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get cities by timezoneId"));
        }
    }

    private Pageable createPageable(int page, int size, String sort, String order){
        Pageable pageable;

        page = page <= 0 ? 1 : page - 1;
        size = size <= 0 ? 10 : size;

        if(!StringUtils.isAllBlank(sort)){
            var orderBy = StringUtils.isBlank(order) ? Sort.Direction.ASC : Sort.Direction.valueOf(order.toUpperCase());

            if(!isSortByValid(sort)){
                throw new InvalidException(ErrorMessage.INVALID_SORT_BY.formatted(sort));
            }

            pageable = PageRequest.of(page, size, orderBy, sort);
        }else {
            pageable = PageRequest.of(page, size);
        }

        return pageable;
    }

    private boolean isSortByValid(String sortBy){
        return switch (sortBy){
            case CITY_NAME, HIGHLIGHTED -> true;
            default -> false;
        };
    }

    private Map<String, Object> emptyCityInfoMap(){
        Map<String, Object> cityInfo = new LinkedHashMap<>();
        cityInfo.put(ID, "");
        cityInfo.put(CITY_NAME, "");
        cityInfo.put(COUNTRY_NAME, "");
        cityInfo.put(CONTINENT_NAME, "");
        cityInfo.put(HIGHLIGHTED, "");
        cityInfo.put(TIMEZONE_NAME, "");
        cityInfo.put(REGION_NAME, "");
        return cityInfo;
    }
}
