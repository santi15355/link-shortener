package com.shortit4me.repository;

import com.shortit4me.model.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {
    Optional<ShortLink> findByGeneratedLink(String generatedLink);
}
