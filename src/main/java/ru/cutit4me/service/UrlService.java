package ru.cutit4me.service;

import ru.cutit4me.model.Url;

import java.util.List;

public interface UrlService {
    Url saveUrl(Url url);
    String findLongLink(String shortLink);

    List<Url> showAllUrls();

}
