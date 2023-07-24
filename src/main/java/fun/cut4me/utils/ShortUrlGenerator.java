package fun.cut4me.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class ShortUrlGenerator {
    public String generateShortLink() {
        StringBuilder shortLinkBuilder = new StringBuilder();
        Random random = new Random();
        return shortLinkBuilder
                .append((char) (random.nextInt(26) + 'A'))
                .append((char) (random.nextInt(26) + 'a'))
                .append(random.nextInt(9))
                .append((char) (random.nextInt(26) + 'A'))
                .append((char) (random.nextInt(26) + 'a'))
                .toString();
    }
}
