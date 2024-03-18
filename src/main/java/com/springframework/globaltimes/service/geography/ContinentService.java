package com.springframework.globaltimes.service.geography;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.entity.geography.Continent;
import com.springframework.globaltimes.exception.NotFoundException;
import com.springframework.globaltimes.repository.geography.ContinentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContinentService {
    public final ContinentRepository continentRepository;

    public Continent getContinentById(String id){
        log.info("Get continent by id:{}",id);
        return continentRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND.formatted("Continent")));
    }
}
