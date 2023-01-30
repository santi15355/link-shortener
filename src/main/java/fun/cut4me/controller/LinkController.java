package fun.cut4me.controller;

import fun.cut4me.model.LongLink;
import fun.cut4me.model.ShortLink;
import fun.cut4me.repository.LongLinkRepository;
import fun.cut4me.repository.ShortLinkRepository;
import fun.cut4me.service.LongLinkService;
import fun.cut4me.utils.ShortLinkGenerator;
import fun.cut4me.utils.UrlChecker;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class LinkController {

    public static final String SHORT_LINK = "{shortLink}";
    public static final String BASE_URL = "https://cut4me.fun/";

    private final UrlChecker urlChecker;

    private final ShortLinkGenerator shortLinkGenerator;
    private final LongLinkService longLinkService;

    private final LongLinkRepository longLinkRepository;
    private final ShortLinkRepository shortLinkRepository;

    @GetMapping
    public ModelAndView showHome() {
        return new ModelAndView("home");
    }

    @PostMapping
    @Transactional
    public ModelAndView addLinks(@RequestParam("link") String link, final RedirectAttributes redirectAttributes) {

        ModelAndView home = new ModelAndView("home");


        if (!urlChecker.checkUrl(link)) {
            home.addObject("message", "Некорректный УРЛ");
            return home;
        }

        LongLink longLink = new LongLink();
        longLink.setUserLink(link);
        longLinkService.saveNewLongLink(longLink);
        ShortLink shortLink = new ShortLink();
        shortLink.setGeneratedLink(shortLinkGenerator.generateShortLink());
        shortLink.setId(longLink.getId());
        shortLinkRepository.save(shortLink);
        ModelAndView shortUrlPage = new ModelAndView("showShortUrl");
        String urlToShow = BASE_URL + shortLink.getGeneratedLink();
        shortUrlPage.addObject("showUrl", urlToShow);
        return shortUrlPage;
    }

    @GetMapping(SHORT_LINK)
    public RedirectView getLongLink(@PathVariable final String shortLink) {

        List<ShortLink> shortLinkList = shortLinkRepository.findAll();
        val id = shortLinkRepository.findByGeneratedLink(shortLink).get().getId();
        return new RedirectView(longLinkRepository.findById(String.valueOf(id)).get().getUserLink());
    }
}
