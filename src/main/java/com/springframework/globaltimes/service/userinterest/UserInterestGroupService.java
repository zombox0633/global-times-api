package com.springframework.globaltimes.service.userinterest;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.dto.userinterest.UserInterestRequest;
import com.springframework.globaltimes.entity.userinterest.UserInterestGroup;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.user.UserRepository;
import com.springframework.globaltimes.repository.userinterest.CustomUserInterestGroupRepository;
import com.springframework.globaltimes.repository.userinterest.UserInterestGroupDataRepository;
import com.springframework.globaltimes.repository.userinterest.UserInterestGroupRepository;
import com.springframework.globaltimes.utils.PageableUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInterestGroupService {

    public final UserInterestGroupRepository userInterestGroupRepository;
    public final CustomUserInterestGroupRepository customUserInterestGroupRepository;
    public final UserInterestGroupDataRepository userInterestGroupDataRepository;

    public final UserRepository userRepository;

    public UserInterestGroup findUserInterestGroupByUserId(String id){
        log.info("UserInterestGroup city by id:{}",id);
        return userInterestGroupRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("UserInterestGroup")));
    }

    public Page<Map<String, Object>> getUserInterestGroupByUserId(String id, int page, int size){
        try {
            var pageable = PageableUtils.createPageable(page, size);
            return customUserInterestGroupRepository.findUserInterestGroupsByUserId(UUID.fromString(id), pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get UserInterestGroup by id"));
        }
    }

    public Page<Map<String, Object>> getUserInterestGroupDataById(String id, int page, int size){
        try {
            var pageable = PageableUtils.createPageable(page,size);
            return userInterestGroupDataRepository.findInterestUserGroupDataById(UUID.fromString(id), pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get UserInterestGroupData by id"));
        }
    }

    public UserInterestGroup createUserInterestGroup(UserInterestRequest request){
        try {
            var existsUser = userRepository.existsById(request.lastOpId());
            if(!existsUser){
                throw new InvalidException("LastOP ID does not exist.");
            }
            if(request.name() == null){
                throw new InvalidException("Name cannot be null.");
            }

            var userInterestGroup = new UserInterestGroup();
            userInterestGroup.setName(request.name());
            userInterestGroup.setDescription(request.description());
            userInterestGroup.setLastOpId(request.lastOpId());

            return userInterestGroupRepository.save(userInterestGroup);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("create UserInterestGroupData"));
        }
    }

    public UserInterestGroup updateUserInterestGroup(String id, UserInterestRequest request){
        try {
            var existsUser = userRepository.existsById(request.lastOpId());
            if(!existsUser){
                throw new InvalidException("LastOP ID does not exist.");
            }

            var existsUserInterest = findUserInterestGroupByUserId(id);
            if(request.name() != null){
                existsUserInterest.setName(request.name());
            }
            if (request.description() != null){
                existsUserInterest.setDescription(request.description());
            }

            existsUserInterest.setLastOpId(request.lastOpId());
            userInterestGroupRepository.save(existsUserInterest);

            return existsUserInterest;
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("update UserInterestGroupData"));
        }
    }

    public UserInterestGroup deleteUserInterestGroup(String id, String userId){
        try {
            var existsUserInterest = findUserInterestGroupByUserId(id);
            var existsUser = userRepository.existsById(UUID.fromString(userId));
            if(!existsUser){
                throw new InvalidException("User ID does not exist.");
            }
            if (!existsUserInterest.getLastOpId().equals(UUID.fromString(userId))){
                throw new InvalidException("User does not have permission to delete this UserInterestGroup.");
            }

            userInterestGroupRepository.delete(existsUserInterest);
            return existsUserInterest;
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("delete UserInterestGroupData"));
        }
    }
}
