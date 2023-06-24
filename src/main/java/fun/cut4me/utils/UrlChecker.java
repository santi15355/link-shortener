package fun.cut4me.utils;


import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

@Component
public class UrlChecker {
    public boolean checkUrl(String url) {
        String[] schemes = {"http", "https", "ftp"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        return urlValidator.isValid(url);
    }
}
