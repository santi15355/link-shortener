package ru.cutit4me.repository;

import ru.cutit4me.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UrlRepository extends JpaRepository<Url, String> {
    Optional<Url> findLongUrlByShortUrl(String url);
}
