package com.springframework.globaltimes.service.time;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.dto.time.CreateTimezoneRequest;
import com.springframework.globaltimes.entity.time.Timezone;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.CountryRepository;
import com.springframework.globaltimes.repository.time.TimezoneRepository;
import com.springframework.globaltimes.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimezoneService {

    public final TimezoneRepository timezoneRepository;
    public final CountryRepository countryRepository;
    public final UserRepository userRepository;

    //Get By ID
    public Timezone getTimezoneById(String id){
        log.info("Get user by id:{}",id);
        return timezoneRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Timezone")));
    }

    //Get By ID
    public Timezone getTimezoneByCountryId(String countryId){
        log.info("Get user by id:{}",countryId);
        return timezoneRepository.findByCountryId(UUID.fromString(countryId))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Timezone")));
    }

    //Create Timezone
    public Timezone createTimezone(CreateTimezoneRequest request){
        try {
            var existsCountry = countryRepository.existsById(request.countryId());
            var existsUser = userRepository.existsById(request.lastOpId());

            if(!existsCountry){
                throw new InvalidException("Country ID does not exist.");
            }
            if(!existsUser){
                throw new InvalidException("LastOP ID does not exist.");
            }

            var timezone = new Timezone();
            timezone.setCountryId(request.countryId());
            timezone.setTimezoneName(request.timezoneName());
            timezone.setLastOpId(request.lastOpId());

            return timezoneRepository.save(timezone);

        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("change password"));
        }
    }

    //daylight saving time
    public String createDST(String timezoneName){
        var timezoneId = ZoneId.of(timezoneName);
        var currentZonedDateTime  = ZonedDateTime.now(timezoneId);
        return  currentZonedDateTime.getOffset().toString();
    }
}
