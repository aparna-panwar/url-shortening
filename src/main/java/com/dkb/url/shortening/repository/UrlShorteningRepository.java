package com.dkb.url.shortening.repository;

import com.dkb.url.shortening.model.entity.UrlShortenMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShorteningRepository extends JpaRepository<UrlShortenMapping, Long> {

    UrlShortenMapping findByShortUrl(String shortUrlCode);

    UrlShortenMapping findByOriginalUrl(String originalUrl);

}
