package com.shortit4me.service;

import com.shortit4me.model.ShortLink;
import com.shortit4me.repository.ShortLinkRepository;
import org.springframework.stereotype.Service;

@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    private ShortLinkRepository shortLinkRepository;

    @Override
    public ShortLink saveNewShortLink(ShortLink shortLink) {
        return shortLinkRepository.save(shortLink);
    }
}
