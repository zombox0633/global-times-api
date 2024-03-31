package com.springframework.globaltimes.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SelectData {
    public static final String SELECT_DATA_CITY = "SELECT ci.id, ci.city_name, co.country_name, ct.continent_name, ci.highlighted tz.timezone_name, ct.region_name FROM cities ci ";
    public static final String CITY_JOIN_TIMEZONE = "INNER JOIN timezones tz ON tz.id = ci.timezones_id ";
    public static final String CITY_JOIN_COUNTRY = "INNER JOIN countries co ON co.id = tz.country_id ";
    public static final String CITY_JOIN_CONTINENT = "INNER JOIN continents ct ON ct.id = co.continent_id ";
    public static final String WHERE_CITY = "WHERE ci.id = %:cityId% ";
    public static final String WHERE_COUNTRY_ID = "WHERE ci.id = %:cityId%";

    public static final String SELECT_DATA_BY_CITY_ID = SELECT_DATA_CITY + CITY_JOIN_TIMEZONE + CITY_JOIN_COUNTRY + CITY_JOIN_CONTINENT + WHERE_CITY;


}
