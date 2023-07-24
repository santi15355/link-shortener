package fun.cut4me.controller;

import fun.cut4me.model.Url;
import fun.cut4me.repository.UrlRepository;
import fun.cut4me.service.UrlService;
import fun.cut4me.utils.ShortUrlGenerator;
import fun.cut4me.utils.UrlChecker;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class UrlController {

    public static final String SHORT_LINK = "{shortLink}";
    public static final String BASE_URL = "https://cut4me.fun/";
    private final UrlChecker urlChecker;
    private final ShortUrlGenerator shortUrlGenerator;

    @Autowired
    private final UrlRepository urlRepository;
    @Autowired
    private final UrlService urlService;

    @GetMapping
    public ModelAndView showHome() {
        return new ModelAndView("home");
    }

    @PostMapping
    @Transactional
    public ModelAndView addLinks(@RequestParam("link") String link) {

        ModelAndView home = new ModelAndView("home");

        if (!urlChecker.checkUrl(link)) {
            home.addObject("message", "Некорректный УРЛ");
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
            return shortUrlPage;
        }
    }

    @GetMapping(SHORT_LINK)
    @SneakyThrows
    public RedirectView getLongLink(@PathVariable final String shortLink) {
        return new RedirectView(urlService.findLongLink(shortLink));
    }

    @GetMapping("/show")
    public ModelAndView showAllUrls() {
        ModelAndView showUrls = new ModelAndView("showAll");
        List<Url> urls = urlRepository.findAll().stream().toList();
        showUrls.addObject("links", urls);
        return showUrls;
    }
}
