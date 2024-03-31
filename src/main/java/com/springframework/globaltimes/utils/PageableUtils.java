package com.springframework.globaltimes.utils;

import com.springframework.globaltimes.constants.ErrorMessage;
import com.springframework.globaltimes.exception.InvalidException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableUtils {

    public static Pageable createPageable(int page, int size){
        Pageable pageable;

        page = page <= 0 ? 1 : page - 1;
        size = size <= 0 ? 10 : size;

        pageable = PageRequest.of(page, size);

        return pageable;
    }

    public static boolean isSortByValid(String sortBy){
        return switch (sortBy){
            case "city_name", "country_name", "continent_name", "highlighted", "timezone_name", "region_name" -> true;
            default -> false;
        };
    }
}
