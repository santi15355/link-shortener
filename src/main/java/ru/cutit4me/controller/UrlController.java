package ru.cutit4me.controller;

import ru.cutit4me.model.Url;
import ru.cutit4me.service.UrlService;
import ru.cutit4me.utils.ShortUrlGenerator;
import ru.cutit4me.utils.UrlChecker;
import ru.cutit4me.utils.UrlEncoder;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class UrlController {
    private static final Logger LOGGER = LoggerFactory.getLogger("Url controller");

    public static final String SHORT_LINK = "/{shortLink}";
    public static final String BASE_URL = "https://cutit4me.ru/";
    private final UrlChecker urlChecker;
    private final ShortUrlGenerator shortUrlGenerator;
    private final UrlEncoder urlEncoder;

    @Autowired
    private final UrlService urlService;

    @GetMapping
    public ModelAndView showHome() {
        LOGGER.info("Main page opened");
        return new ModelAndView("home");
    }

    @PostMapping
    @Transactional
    @SneakyThrows
    public ModelAndView addLinks(@RequestParam("link") String link) {

        ModelAndView home = new ModelAndView("home");

        if (!urlChecker.checkUrl(link)) {
            home.addObject("message", "Некорректный УРЛ");
            LOGGER.info("Wrong URL " + link);
            return home;

        } else {
            Url url = new Url();
            url.setLongUrl(link);
            String shortLink = shortUrlGenerator.generateShortLink();
            url.setShortUrl(shortLink);
            urlService.saveUrl(url);
            ModelAndView shortUrlPage = new ModelAndView("showShortUrl");
            String urlToShow = BASE_URL + shortLink;
            shortUrlPage.addObject("showUrl", urlToShow);
            LOGGER.info("URLs successfully added in DB");
            return shortUrlPage;
        }
    }

    @RequestMapping(value = SHORT_LINK, method = RequestMethod.GET)
    public ModelAndView getLongLink(@PathVariable final String shortLink) {
        LOGGER.info("Long URL successfully found!");
        return new ModelAndView("redirect:" + UrlEncoder.encodeUrl(urlService.findLongLink(shortLink)));
    }

    @GetMapping("/show")
    public ModelAndView showAllUrls() {
        ModelAndView showUrls = new ModelAndView("showAll");
        List<Url> urls = urlService.showAllUrls();
        showUrls.addObject("links", urls);
        return showUrls;
    }
}
