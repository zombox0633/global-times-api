package com.springframework.globaltimes.repository.geography.country;

import com.springframework.globaltimes.entity.geography.Country;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CountrySpecifications {

    public Specification<Country> nameLike(String name){
        return ((root, query, criteriaBuilder) -> {
            query.orderBy(criteriaBuilder.asc(root.get("name")));
            return  criteriaBuilder
                    .like(criteriaBuilder.lower(root.get("name")),  name.toLowerCase() + "%");
        }
        );
    }

}
