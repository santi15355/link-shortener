package com.shortit4me.repository;

import com.shortit4me.model.LongLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LongLinkRepository extends JpaRepository<LongLink, String> {

}
