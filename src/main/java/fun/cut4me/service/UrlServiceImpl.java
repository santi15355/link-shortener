package fun.cut4me.service;

import fun.cut4me.model.Url;
import fun.cut4me.repository.UrlRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@CacheConfig(cacheNames = "urlCache")
public class UrlServiceImpl implements UrlService {

    private static final Logger LOGGER = LoggerFactory.getLogger("Url service");
    private String shortUrl;

    @Autowired
    private UrlRepository urlRepository;

    @Override
    @Cacheable(cacheNames = "urls")
    public Url saveUrl(Url url) {
        LOGGER.info("Urls saved");
        return urlRepository.save(url);
    }

    @Override
    @SneakyThrows
    @Cacheable(cacheNames = "urls")
    public String findLongLink(String shortLink) {
        try {
            shortUrl = urlRepository.findLongUrlByShortUrl(shortLink).get().getLongUrl();
        } catch (Exception ignored) {

        }
        LOGGER.info("Long url found!");
        return shortUrl;
    }

    @Override
    @CachePut(cacheNames = "urls")
    public List<Url> showAllUrls() {
        return urlRepository.findAll().stream().toList();
    }

    @Override
    @CachePut(cacheNames = "urls")
    public List<Url> getAllUrlsForCache() {
        return urlRepository.findAll();
    }
}
