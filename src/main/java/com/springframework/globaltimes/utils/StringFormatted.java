package com.springframework.globaltimes.utils;

import com.springframework.globaltimes.constants.ErrorMessage;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class StringFormatted {
    public static String slugToText(String text){
        if (text == null){
            log.error("Attempted to convert null slug to text.");
            throw new IllegalArgumentException("Slug cannot be null");
        }
        return text.trim().replace("-", " ").toLowerCase();
    }
}
