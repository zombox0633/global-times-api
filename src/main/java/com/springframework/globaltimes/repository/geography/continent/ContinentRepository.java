package com.springframework.globaltimes.repository.geography.continent;

import com.springframework.globaltimes.entity.geography.Continent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContinentRepository extends JpaRepository<Continent, UUID> {
    List<Continent> findAll();
}
