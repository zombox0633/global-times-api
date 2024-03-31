package com.springframework.globaltimes.repository.interest;

import com.springframework.globaltimes.entity.interest.InterestGroupCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InterestGroupCityRepository extends JpaRepository<InterestGroupCity, UUID> {
    List<InterestGroupCity> findAll();

}
