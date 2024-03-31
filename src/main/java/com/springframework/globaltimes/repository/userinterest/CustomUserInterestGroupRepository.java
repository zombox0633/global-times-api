package com.springframework.globaltimes.repository.userinterest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface CustomUserInterestGroupRepository extends UserInterestGroupRepository{
    @Query(value = "SELECT ui.id, ui.group_name, ui.description, ui.create_timestamp, ui.last_updated_timestamp, ui.last_op_id " +
            "FROM user_interest_groups ui " +
            "INNER JOIN users u ON ui.last_op_id = u.id " +
            "WHERE u.id = %:id% ", nativeQuery = true)
    Page<Map<String, Object>> findUserInterestGroupsByUserId(UUID id, Pageable pageable);
}
