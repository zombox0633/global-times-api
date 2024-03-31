package com.springframework.globaltimes.repository.geography.city;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.UUID;

@Repository
public interface IdCityRepository extends CityRepository{

    @Query(value = "SELECT ci.id, ci.city_name, co.country_name, ct.continent_name, ci.highlighted, tz.timezone_name, ct.region_name "
            + "FROM cities ci "
            + "INNER JOIN timezones tz ON tz.id = ci.timezones_id "
            + "INNER JOIN countries co ON co.id = tz.country_id "
            + "INNER JOIN continents ct ON ct.id = co.continent_id "
            + "WHERE ci.id = %:cityId%", nativeQuery = true)
    Map<String, Object> findCityById(UUID cityId);
}
