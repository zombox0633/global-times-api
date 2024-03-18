package com.springframework.globaltimes.repository.geography.city;

import com.springframework.globaltimes.entity.geography.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CityRepository extends JpaRepository<City, UUID> {
    List<City> findAll();

    Page<City> findByTimezoneId(UUID timezoneId, Pageable pageable);

}
