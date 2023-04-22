package fun.cut4me.repository;

import fun.cut4me.model.LongLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LongLinkRepository extends JpaRepository<LongLink, String> {
    Optional<Object> findById(Long id);
}
