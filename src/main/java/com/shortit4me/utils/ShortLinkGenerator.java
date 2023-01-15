package com.shortit4me.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class ShortLinkGenerator {

    private final StringBuilder shortLinkBuilder = new StringBuilder();
    private final Random random = new Random();

    public String generateShortLink() {
        return shortLinkBuilder
                .append((char) (random.nextInt(26) + 'A'))
                .append((char) (random.nextInt(26) + 'a'))
                .append(random.nextInt(9))
                .append((char) (random.nextInt(26) + 'A'))
                .append((char) (random.nextInt(26) + 'a'))
                .toString();
    }

}
