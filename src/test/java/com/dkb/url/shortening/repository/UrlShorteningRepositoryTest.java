package com.dkb.url.shortening.repository;

import com.dkb.url.shortening.model.entity.UrlShortenMapping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UrlShorteningRepositoryTest {

    @Autowired
    private UrlShorteningRepository urlShorteningRepository;

    @Test
    public void testSaveShortUrlAndFetchActualUrlFromShortUrl() {
        UrlShortenMapping mapping = new UrlShortenMapping();
        mapping.setOriginalUrl("https://community.cadence.com/cadence_blogs_8/b/fv/posts/understanding-latency-vs-throughput");
        mapping.setShortUrl("QWY67Ht");

        UrlShortenMapping savedMapping = urlShorteningRepository.save(mapping);
        UrlShortenMapping fetchedMapping = urlShorteningRepository.findByShortUrl("QWY67Ht");

        assertNotNull(savedMapping.getId());
        assertEquals("https://community.cadence.com/cadence_blogs_8/b/fv/posts/understanding-latency-vs-throughput", fetchedMapping.getOriginalUrl());
    }

    @Test
    public void testSaveShortUrlAndFetchShortUrlFromActualUrl() {
        UrlShortenMapping mapping = new UrlShortenMapping();
        mapping.setOriginalUrl("https://community.cadence.com/cadence_blogs_8/b/fv/posts/understanding-latency-vs-throughput");
        mapping.setShortUrl("QWY67Ht");

        UrlShortenMapping savedMapping = urlShorteningRepository.save(mapping);
        UrlShortenMapping fetchedMapping = urlShorteningRepository.findByOriginalUrl("https://community.cadence.com/cadence_blogs_8/b/fv/posts/understanding-latency-vs-throughput");

        assertNotNull(savedMapping.getId());
        assertEquals("QWY67Ht", fetchedMapping.getShortUrl());
    }


}
