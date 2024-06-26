package com.springframework.globaltimes.service.geography;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.dto.geography.CreateCityRequest;
import com.springframework.globaltimes.dto.geography.UpdateCityRequest;
import com.springframework.globaltimes.entity.geography.City;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.city.CityNameRepository;
import com.springframework.globaltimes.repository.geography.city.CityRepository;
import com.springframework.globaltimes.repository.geography.city.CitySpecifications;
import com.springframework.globaltimes.repository.geography.city.ContinentCityRepository;
import com.springframework.globaltimes.repository.geography.city.CountyCityRepository;
import com.springframework.globaltimes.repository.geography.city.IdCityRepository;
import com.springframework.globaltimes.repository.geography.city.TimezoneCityRepository;
import com.springframework.globaltimes.repository.time.TimezoneRepository;
import com.springframework.globaltimes.repository.user.UserRepository;
import com.springframework.globaltimes.utils.PageableUtils;
import com.springframework.globaltimes.utils.StringFormatted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CityService {
    private final CitySpecifications citySpecifications;

    public final CityRepository cityRepository;
    public final CityNameRepository cityNameRepository;
    public final TimezoneRepository timezoneRepository;
    public final UserRepository userRepository;

    public final IdCityRepository idCityRepository;
    public final TimezoneCityRepository timezoneCityRepository;
    public final CountyCityRepository countyCityRepository;
    public final ContinentCityRepository continentCityRepository;

    //GET BY ID
    public City getCityById(String id){
        log.info("Get city by id:{}",id);
        return cityRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("City")));
    }

    public Page<City> getAllCityWithSearch( String cityName, int page, int size ){
        var formattedCityName = StringFormatted.slugToText(cityName); 
        Specification<City> spec = Specification.where(citySpecifications.nameLike(formattedCityName));
        var pageable = PageableUtils.createPageable(page, size);

        return cityRepository.findAll(spec, pageable);
    }

    //Get Data By ID
    public Map<String, Object> getCityDataById(String id){
        try {
            log.info("Get city data by id:{}",id);
            return idCityRepository.findCityById(UUID.fromString(id));
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get city by id"));
        }
    }

    //Get By CityName
    public List<Map<String, Object>> getCityByName(List<String> cityName){
        try {
            var formattedCityName = cityName.stream().map(StringFormatted::slugToText).toList();
            return cityNameRepository.findCityByName(formattedCityName);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get city by cityName"));
        }
    }

    //Get By TimezoneId
    public Page<Map<String, Object>> getCitiesByTimezoneId(String timezoneId, int page, int size){
        try {
            var pageable = PageableUtils.createPageable(page, size);
            return timezoneCityRepository.findCityByTimezoneId(UUID.fromString(timezoneId), pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get cities by timezoneId"));
        }
    }

    //Get By CountryId
    public Page<Map<String, Object>> getCitiesByCountryId(String countryId, int page, int size){
        try {
            var pageable = PageableUtils.createPageable(page, size);
            return countyCityRepository.findCityByCountyId(UUID.fromString(countryId), pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get cities by countryId"));
        }
    }

    public Page<Map<String, Object>> getCitiesByContinentId(String continentId, int page, int size){
        try {
            var pageable = PageableUtils.createPageable(page, size);
            return continentCityRepository.findCityByContinentId(UUID.fromString(continentId), pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get cities by continentId"));
        }
    }

    //Post city
    public City createCity(CreateCityRequest request){
        try {
            var existsTimezone = timezoneRepository.existsById(request.timezoneId());
            var existsUser = userRepository.existsById(request.lastOpId());

            if(!existsTimezone){
                throw new InvalidException("Timezone ID does not exist.");
            }
            if(!existsUser){
                throw new InvalidException("LastOP ID does not exist.");
            }

            var city = new City();
            city.setTimezoneId(request.timezoneId());
            city.setName(request.cityName());
            city.setHighlighted(request.highlighted());
            city.setLastOpId(request.lastOpId());

            return cityRepository.save(city);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("create city"));
        }
    }

    //Update city
    public City updateCity(String id, UpdateCityRequest request){
        try {
            var existsCity = getCityById(id);
            var existsUser = userRepository.existsById(request.lastOpId());

            if(!existsUser){
                throw new InvalidException("LastOP ID does not exist.");
            }
            if (request.timezoneId() != null){
                var existsTimezone = timezoneRepository.existsById(request.timezoneId());
                if(!existsTimezone){
                    throw new InvalidException("Timezone ID does not exist.");
                }
                existsCity.setTimezoneId(request.timezoneId());
            }

            if(request.cityName() != null){
                existsCity.setName(request.cityName());
            }
            if (request.highlighted() != null){
                existsCity.setHighlighted(request.highlighted());
            }

            existsCity.setLastOpId(request.lastOpId());
            cityRepository.save(existsCity);

            return existsCity;
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("update city"));
        }
    }
}
