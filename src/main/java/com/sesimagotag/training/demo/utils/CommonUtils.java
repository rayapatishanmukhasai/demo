package com.sesimagotag.training.demo.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {
    public static String reverseString(String originalString) {
        String reversedString = "";
        char ch;
        for (int i = 0; i < originalString.length(); i++) {
            ch= originalString.charAt(i);
            reversedString= ch+reversedString;
        }

        log.info("reverseString - String name reversed from {} to {}", originalString, reversedString);
        return reversedString;
    }
}
