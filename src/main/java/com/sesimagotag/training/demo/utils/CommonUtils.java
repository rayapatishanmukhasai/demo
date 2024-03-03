package com.sesimagotag.training.demo.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {
    public static String reverseString(String originalString) {
        StringBuilder reversedString = new StringBuilder();
        char ch;
        for (int i = 0; i < originalString.length(); i++) {
            ch= originalString.charAt(i);
            reversedString.insert(0, ch);
        }

        log.info("reverseString - String name reversed from {} to {}", originalString, reversedString);
        return reversedString.toString();
    }
}
