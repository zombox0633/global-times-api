package com.springframework.globaltimes.service.geography;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.entity.geography.Continent;
import com.springframework.globaltimes.exception.InvalidException;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.continent.ContinentByNameRepository;
import com.springframework.globaltimes.repository.geography.continent.ContinentRepository;
import com.springframework.globaltimes.utils.PageableUtils;
import com.springframework.globaltimes.utils.StringFormatted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContinentService {

    public final ContinentRepository continentRepository;
    public final ContinentByNameRepository continentByNameRepository;

    public Continent getContinentById(String id){
        log.info("Get continent by id:{}",id);
        return continentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Continent")));
    }

    public Page<Map<String, Object>> getContinentDataByName(String name, int page, int size){
        try {
            var formattedContinentName = StringFormatted.slugToText(name);
            var pageable = PageableUtils.createPageable(page, size);
            return continentByNameRepository.findContinentByName(formattedContinentName, pageable);
        }catch (InvalidException e){
            log.error(ErrorMessage.INVALID_REQUEST_LOG.formatted(e.getMessage()), e);
            throw e;
        }catch (Exception e){
            log.error(ErrorMessage.EXCEPTION_REQUEST_LOG.formatted(e.getMessage()),e);
            throw new InvalidException(ErrorMessage.FAILED_LOG.formatted("get continent data by name"));
        }
    }
}
