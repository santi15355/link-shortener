package fun.cut4me.service;

import fun.cut4me.model.Url;

public interface UrlService {
    Url saveUrl(Url url);
    String findLongLink(String shortLink);
}
