package com.springframework.globaltimes.repository.userinterest;

import com.springframework.globaltimes.entity.userinterest.UserInterestGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public interface UserInterestGroupRepository extends JpaRepository<UserInterestGroup, UUID> {
    List<UserInterestGroup> findAll();

}
