package com.shortit4me.repository;

import com.shortit4me.model.ShortLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortLinkRepository extends JpaRepository<ShortLink, String> {

}
