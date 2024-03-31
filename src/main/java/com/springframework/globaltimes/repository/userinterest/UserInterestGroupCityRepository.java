package com.springframework.globaltimes.repository.userinterest;

import com.springframework.globaltimes.entity.userinterest.UserInterestGroupCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserInterestGroupCityRepository extends JpaRepository<UserInterestGroupCity, UUID> {
    List<UserInterestGroupCity> findAll();
}
