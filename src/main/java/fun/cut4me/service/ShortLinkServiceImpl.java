package fun.cut4me.service;

import fun.cut4me.model.ShortLink;
import fun.cut4me.repository.ShortLinkRepository;
import org.springframework.stereotype.Service;

@Service
public class ShortLinkServiceImpl implements ShortLinkService {

    private ShortLinkRepository shortLinkRepository;

    @Override
    public ShortLink saveNewShortLink(ShortLink shortLink) {
        return shortLinkRepository.save(shortLink);
    }
}
