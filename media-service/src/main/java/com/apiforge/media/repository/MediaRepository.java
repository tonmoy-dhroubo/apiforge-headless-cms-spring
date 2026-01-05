package com.apiforge.media.repository;

import com.apiforge.media.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    boolean existsByName(String name);
    Optional<Media> findByHashAndExt(String hash, String ext);
}
