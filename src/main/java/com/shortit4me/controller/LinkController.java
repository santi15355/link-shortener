package com.shortit4me.controller;

import com.shortit4me.model.LongLink;
import com.shortit4me.model.ShortLink;
import com.shortit4me.repository.LongLinkRepository;
import com.shortit4me.repository.ShortLinkRepository;
import com.shortit4me.service.LongLinkService;
import com.shortit4me.utils.ShortLinkGenerator;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
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
public class LinkController {

    public static final String SHORT_LINK = "{shortLink}";
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;
    @Autowired
    private LongLinkService longLinkService;
    @Autowired
    private LongLinkRepository longLinkRepository;

    @Autowired
    private ShortLinkRepository shortLinkRepository;

    @GetMapping
    public ModelAndView showHome() {
        return new ModelAndView("home");
    }

    /*@PostMapping
    @Transactional
    public String addLinks(@RequestParam("link") String link, Model model) {
        LongLink longLink = new LongLink();
        longLink.setUserLink(link);
        longLinkService.saveNewLongLink(longLink);
        ShortLink shortLink = new ShortLink();
        shortLink.setGeneratedLink(shortLinkGenerator.generateShortLink());
        shortLink.setId(longLink.getId());
        shortLinkRepository.save(shortLink);
        return "redirect:/";
    }*/

    @PostMapping
    @Transactional
    public String addLinks(@RequestParam("link") String link, Model model) {
        LongLink longLink = new LongLink();
        longLink.setUserLink(link);
        longLinkService.saveNewLongLink(longLink);
        ShortLink shortLink = new ShortLink();
        shortLink.setGeneratedLink(shortLinkGenerator.generateShortLink());
        shortLink.setId(longLink.getId());
        shortLinkRepository.save(shortLink);
        //model.addAttribute("shortUrl", shortLink.getGeneratedLink());
        return "redirect:/showShortUrl";
    }

    @GetMapping(SHORT_LINK)
    public String getLongLink(@PathVariable final String shortLink) {

        List<ShortLink> shortLinkList = shortLinkRepository.findAll();
        val linkFromRepo = shortLinkRepository.findByGeneratedLink(shortLink).get().getId();
        return String.valueOf(longLinkRepository.findById(String.valueOf(linkFromRepo)));
    }
}
