package com.springframework.globaltimes.repository.geography.continent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface ContinentByNameRepository extends ContinentRepository {

    @Query(value = "SELECT ct.id, co.id as country_id, ct.continent_name, ct.region_name, co.country_name "
            + "FROM continents ct "
            + "INNER JOIN countries co ON co.continent_id = ct.id "
            + "WHERE LOWER(ct.continent_name) = :name "
            + "ORDER BY ct.id, co.id, ct.continent_name, ct.region_name, co.country_name " , nativeQuery = true)
    Page<Map<String, Object>> findContinentByName(String name, Pageable pageable);
}
