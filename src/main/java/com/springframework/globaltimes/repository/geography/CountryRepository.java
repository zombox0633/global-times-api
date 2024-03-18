package com.springframework.globaltimes.repository.geography;

import com.springframework.globaltimes.entity.geography.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {
    List<Country> findAll();
}
