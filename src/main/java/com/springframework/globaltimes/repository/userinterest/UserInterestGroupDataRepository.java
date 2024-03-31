package com.springframework.globaltimes.repository.userinterest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface UserInterestGroupDataRepository extends UserInterestGroupRepository{

    @Query(value = "SELECT uic.id, ci.id as city_id, ci.city_name, co.country_name, ct.continent_name, ci.highlighted, tz.timezone_name, ct.region_name " +
            "FROM user_interest_groups ui " +
            "INNER JOIN user_interest_group_cities uic ON ui.id = uic.user_group_id " +
            "INNER JOIN cities ci ON uic.city_id = ci.id " +
            "INNER JOIN timezones tz ON tz.id = ci.timezones_id " +
            "INNER JOIN countries co ON co.id = tz.country_id " +
            "INNER JOIN continents ct ON ct.id = co.continent_id " +
            "WHERE ui.id = %:id% ", nativeQuery = true )
    Page<Map<String, Object>> findInterestUserGroupDataById(UUID id, Pageable pageable);
}
