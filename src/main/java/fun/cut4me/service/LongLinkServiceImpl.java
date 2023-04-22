package fun.cut4me.service;

import fun.cut4me.model.LongLink;
import fun.cut4me.repository.LongLinkRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LongLinkServiceImpl implements LongLinkService {
    private LongLinkRepository longLinkRepository;

    public LongLink saveNewLongLink(LongLink longLink) {
        return longLinkRepository.save(longLink);
    }


}
