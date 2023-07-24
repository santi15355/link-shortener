package fun.cut4me.service;

import fun.cut4me.model.Url;
import fun.cut4me.repository.UrlRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UrlServiceImpl implements UrlService {
    private String shortUrl;

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url saveUrl(Url url) {
        return urlRepository.save(url);
    }

    @Override
    @SneakyThrows
    public String findLongLink(String shortLink) {
        try {
            shortUrl = urlRepository.findLongUrlByShortUrl(shortLink).get().getLongUrl();
        } catch (Exception ignored) {

        }
        return shortUrl;
    }

    @Override
    public List<Url> showAllUrls() {
        return urlRepository.findAll().stream().toList();
    }

}
