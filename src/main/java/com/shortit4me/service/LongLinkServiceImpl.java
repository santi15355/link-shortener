package com.shortit4me.service;

import com.shortit4me.model.LongLink;
import com.shortit4me.repository.LongLinkRepository;
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
