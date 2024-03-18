package com.springframework.globaltimes.repository.time;

import com.springframework.globaltimes.entity.time.Timezone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TimezoneRepository extends JpaRepository<Timezone, UUID> {
    List<Timezone> findAll();

    Optional<Timezone> findByCountryId(UUID countryId);
}
