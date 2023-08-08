package ru.cutit4me.utils;


import lombok.SneakyThrows;
import org.apache.commons.validator.routines.RegexValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import static org.apache.commons.validator.routines.UrlValidator.ALLOW_2_SLASHES;
import static org.apache.commons.validator.routines.UrlValidator.ALLOW_ALL_SCHEMES;
import static org.apache.commons.validator.routines.UrlValidator.ALLOW_LOCAL_URLS;

@Component
public class UrlChecker {
    @SneakyThrows
    public boolean checkUrl(String url) {
        String regex = "[a-zA-Z0-9%-._~!$&'()*+,;=а-яА-Я]";
        RegexValidator regexValidator = new RegexValidator(regex);
        UrlValidator urlValidator = new UrlValidator(regexValidator,
                ALLOW_ALL_SCHEMES + ALLOW_LOCAL_URLS + ALLOW_2_SLASHES);
        return urlValidator.isValid(url);
    }
}
