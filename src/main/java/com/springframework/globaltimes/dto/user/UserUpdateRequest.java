package com.springframework.globaltimes.dto.user;

import java.sql.Date;

public record UserUpdateRequest(

        String name,

        Date birthday
) {
}
