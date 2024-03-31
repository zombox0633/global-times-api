package com.springframework.globaltimes.service.interest;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.entity.interest.InterestGroup;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.interest.InterestGroupRepository;
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
public class InterestGroupService {
    public final InterestGroupRepository interestGroupRepository;

    public InterestGroup getInterestGroupById(String id){
        log.info("Get interestGroup by id:{}",id);
        return interestGroupRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("InterestGroup")));
    }

    public Page<InterestGroup> getInterestGroup(int page, int size){
        var pageable = PageableUtils.createPageable(page, size);
        return interestGroupRepository.findAll(pageable);
    }

    public Page<Map<String, Object>> getInterestGroupDataById(String id, int page, int size){
        try {
            var pageable = PageableUtils.createPageable(page, size);
            return interestGroupRepository.findInterestGroupDataById(UUID.fromString(id), pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get InterestGroupData by id"));
        }
    }
}
