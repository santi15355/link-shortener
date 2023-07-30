package fun.cut4me.service;

import fun.cut4me.model.Url;

import java.util.List;

public interface UrlService {
    Url saveUrl(Url url);
    String findLongLink(String shortLink);

    List<Url> showAllUrls();

}
