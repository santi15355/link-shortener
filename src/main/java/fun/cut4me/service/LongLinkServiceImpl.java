package fun.cut4me.service;

import fun.cut4me.model.LongLink;
import fun.cut4me.repository.LongLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LongLinkServiceImpl implements LongLinkService {
    @Autowired
    private LongLinkRepository longLinkRepository;

    @Override
    public LongLink saveNewLongLink(LongLink longLink) {
        return longLinkRepository.save(longLink);
    }


}
