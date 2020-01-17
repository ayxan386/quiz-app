package com.aykhan.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RandomGen {
    public static String generate(int len) {
        return Stream
                .generate(() -> String.valueOf((int) (Math.random() * 26 + 'A')))
                .limit(len).collect(Collectors.joining(""));
    }
}
