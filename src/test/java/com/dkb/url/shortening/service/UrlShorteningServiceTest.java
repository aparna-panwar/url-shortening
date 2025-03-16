package com.dkb.url.shortening.service;

import com.dkb.url.shortening.exception.UrlNotFoundException;
import com.dkb.url.shortening.model.entity.UrlShortenMapping;
import com.dkb.url.shortening.repository.UrlShorteningRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UrlShorteningServiceTest {

    @Autowired
    private UrlShorteningService urlShorteningService;

    @Autowired
    private UrlShorteningRepository urlShorteningRepository;

    @Test
    public void testShortenUrl() {
        String originalUrl = "https://mylongurl.com";

        String shortUrl = urlShorteningService.shortenUrl(originalUrl);

        assertNotNull(shortUrl);
        assertTrue(shortUrl.startsWith("http://mytinyurl.com/"));
        assertEquals(1, urlShorteningRepository.count());
    }

    @Test
    public void testShortenUrl_ConflictWithSameUrl() {
        String originalUrl = "https://mylongurl.com";
        String firstShortUrl = urlShorteningService.shortenUrl(originalUrl);
        String secondShortUrl = urlShorteningService.shortenUrl(originalUrl);

        assertEquals(firstShortUrl, secondShortUrl);
        assertEquals(1, urlShorteningRepository.count());
    }

    @Test
    public void testGetRedirectionUrl_ValidShortUrl_ShouldReturnActualUrl() {
        String shortUrlCode = "QWY67Ht";
        String actualUrl = "https://mylongurl.com";
        UrlShortenMapping mapping = new UrlShortenMapping();
        mapping.setShortUrl(shortUrlCode);
        mapping.setOriginalUrl(actualUrl);
        urlShorteningRepository.save(mapping);

        String returnedUrl = urlShorteningService.getRetrieveUrl(shortUrlCode);
        assertEquals(actualUrl, returnedUrl);
    }

    @Test
    public void testGetRedirectionUrl_InvalidShortUrl_ShouldThrowUrlNotFoundException() {
        UrlNotFoundException exception = assertThrows(UrlNotFoundException.class, () -> {
            urlShorteningService.getRetrieveUrl("notavailableurl");
        });

        assertEquals("URL not found", exception.getMessage());
    }
}
