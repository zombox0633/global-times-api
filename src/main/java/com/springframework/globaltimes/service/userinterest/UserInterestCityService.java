package com.springframework.globaltimes.service.userinterest;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.dto.userinterest.UserInterestGroupCityRequest;
import com.springframework.globaltimes.dto.userinterest.UserInterestGroupCityRequests;
import com.springframework.globaltimes.entity.userinterest.UserInterestGroupCity;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.city.CityRepository;
import com.springframework.globaltimes.repository.user.UserRepository;
import com.springframework.globaltimes.repository.userinterest.UserInterestGroupCityRepository;
import com.springframework.globaltimes.repository.userinterest.UserInterestGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInterestCityService {

    public final UserInterestGroupCityRepository userInterestGroupCityRepository;
    public final UserRepository userRepository;
    public final CityRepository cityRepository;
    public final UserInterestGroupRepository userInterestGroupRepository;

    public UserInterestGroupCity getUserInterestCityById(String id){
        log.info("UserInterestGroup city by id:{}",id);
        return userInterestGroupCityRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("UserInterestCity")));
    }

    public UserInterestGroupCity createUserInterestCity(UserInterestGroupCityRequest request){
        try {
            var existsUserInterestGroup = userInterestGroupRepository.existsById(request.groupId());
            var existsUser = userRepository.existsById(request.lastOpId());
            var existsCity = cityRepository.existsById(request.cityId());

            if(!existsUser){
                throw new InvalidException("User ID does not exist.");
            }
            if(!existsUserInterestGroup){
                throw new InvalidException("UserInterestGroup ID does not exist.");
            }
            if(!existsCity){
                throw new InvalidException("City ID does not exist.");
            }

            var userInterestCity = new UserInterestGroupCity();
            userInterestCity.setGroupId(request.groupId());
            userInterestCity.setCityId(request.cityId());
            userInterestCity.setLastOpId(request.lastOpId());

            return userInterestGroupCityRepository.save(userInterestCity);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("create UserInterestGroupCity"));
        }
    }

    public List<UserInterestGroupCity> createUserInterestCities(UserInterestGroupCityRequests requests){
        List<UserInterestGroupCity> createdCities = new ArrayList<>();

        for(UserInterestGroupCityRequest request : requests.requests()){
            try {
                var existsUserInterestGroup = userInterestGroupRepository.existsById(request.groupId());
                var existsUser = userRepository.existsById(request.lastOpId());
                var existsCity = cityRepository.existsById(request.cityId());

                if(!existsUser){
                    throw new InvalidException("User ID does not exist.");
                }
                if(!existsUserInterestGroup){
                    throw new InvalidException("UserInterestGroup ID does not exist.");
                }
                if(!existsCity){
                    throw new InvalidException("City ID does not exist.");
                }

                var userInterestCity = new UserInterestGroupCity();
                userInterestCity.setGroupId(request.groupId());
                userInterestCity.setCityId(request.cityId());
                userInterestCity.setLastOpId(request.lastOpId());

                createdCities.add(userInterestGroupCityRepository.save(userInterestCity));
            }catch (InvalidException e){
                log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
                throw e;
            }catch (Exception e){
                log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
                throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("create UserInterestGroupCities"));
            }
        }

        return createdCities;
    }

    public UserInterestGroupCity deleteUserInterestCity(String id, String userId){
        try {
            var existsUserInterestCity = getUserInterestCityById(id);
            var existsUser = userRepository.existsById(UUID.fromString(userId));
            if(!existsUser){
                throw new InvalidException("User ID does not exist.");
            }
            if(!existsUserInterestCity.getLastOpId().equals(UUID.fromString(userId))){
                throw new InvalidException("User does not have permission to delete this UserInterestCity.");
            }

            userInterestGroupCityRepository.delete(existsUserInterestCity);
            return existsUserInterestCity;
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("delete UserInterestGroupCity"));
        }
    }
}
