package com.springframework.globaltimes.repository.geography.country;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface CountyByNameRepository extends CountryRepository {

    @Query(value = "SELECT ci.id, ci.city_name, co.country_name, ct.continent_name, ci.highlighted, tz.timezone_name, ct.region_name "
            + "FROM countries co "
            + "INNER JOIN continents ct ON ct.id = co.continent_id "
            + "INNER JOIN timezones tz ON tz.country_id = co.id "
            + "INNER JOIN cities ci ON ci.timezones_id = tz.id "
            + "WHERE LOWER(co.country_name) = %:name% "
            + "ORDER BY ci.id, ci.city_name, co.country_name, ct.continent_name, ci.highlighted, tz.timezone_name, ct.region_name "
            , nativeQuery = true)
    Page<Map<String, Object>> findCountyByName(String name, Pageable pageable);
}
