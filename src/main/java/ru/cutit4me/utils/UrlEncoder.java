package ru.cutit4me.utils;

import org.apache.catalina.util.URLEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class UrlEncoder {
    public static String encodeUrl(String unEncodedUrl) {
        URLEncoder urlEncoder = new URLEncoder();
        ArrayList<Character> safeCharacters = new ArrayList<>(List.of(
                '-', '?', '.', '_', '~', '!', '$', '&', '\'',
                ')', '*', '+', ',', ';', '=', ':', '@', '/', '('
        ));

        safeCharacters.forEach(urlEncoder::addSafeCharacter);
        urlEncoder.setEncodeSpaceAsPlus(false);

        return urlEncoder.encode(unEncodedUrl, StandardCharsets.UTF_8);
    }
}
