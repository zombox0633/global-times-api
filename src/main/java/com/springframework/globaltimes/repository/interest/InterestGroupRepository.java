package com.springframework.globaltimes.repository.interest;

import com.springframework.globaltimes.entity.interest.InterestGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface InterestGroupRepository extends JpaRepository<InterestGroup, UUID> {
    Page<InterestGroup> findAll(Pageable pageable);

    @Query(value = "SELECT ic.id, ci.id as city_id, ci.city_name, co.country_name, ct.continent_name, ci.highlighted, tz.timezone_name, ct.region_name " +
            "FROM interest_groups ig " +
            "INNER JOIN interest_group_cities ic ON ic.group_id = ig.id " +
            "INNER JOIN cities ci ON ci.id = ic.city_id " +
            "INNER JOIN timezones tz ON tz.id = ci.timezones_id " +
            "INNER JOIN countries co ON co.id = tz.country_id " +
            "INNER JOIN continents ct ON ct.id = co.continent_id " +
            "WHERE ig.id = %:id% " +
            "ORDER BY ic.id, ci.id, ci.city_name, co.country_name, ct.continent_name, ci.highlighted, tz.timezone_name, ct.region_name "
            , nativeQuery = true)
    Page<Map<String, Object>> findInterestGroupDataById(UUID id, Pageable pageable);
}
