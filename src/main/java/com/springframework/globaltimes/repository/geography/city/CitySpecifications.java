package com.springframework.globaltimes.repository.geography.city;

import com.springframework.globaltimes.entity.geography.City;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CitySpecifications {
    public Specification<City> nameLike(String name){
        return ((root, query, criteriaBuilder) -> criteriaBuilder
                .like(criteriaBuilder.lower(root.get("name")), "%"+name.toLowerCase()+"%")
        );
    }
}
