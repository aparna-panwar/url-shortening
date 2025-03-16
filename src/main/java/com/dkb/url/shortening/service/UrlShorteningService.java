package com.dkb.url.shortening.service;

import com.dkb.url.shortening.exception.UrlNotFoundException;
import com.dkb.url.shortening.model.entity.UrlShortenMapping;
import com.dkb.url.shortening.repository.UrlShorteningRepository;
import com.dkb.url.shortening.utility.UrlShortenUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UrlShorteningService {

    private final UrlShorteningRepository urlShorteningRepository;

    public static final String MY_URL = "http://mytinyurl.com/";

    public String shortenUrl(String originalUrl) {
        String shortUrl = "";

        UrlShortenMapping existingUrlShortenMapping = urlShorteningRepository.findByOriginalUrl(originalUrl);
        if(existingUrlShortenMapping != null) {
            shortUrl = existingUrlShortenMapping.getShortUrl();
        } else {
            shortUrl = UrlShortenUtility.urlEncryption(originalUrl);
            urlShorteningRepository.save(new UrlShortenMapping(originalUrl, shortUrl));
        }
        return MY_URL + shortUrl;
}
    public String getRetrieveUrl(String shortUrlCode) {
       UrlShortenMapping shortenUrlMapping = urlShorteningRepository.findByShortUrl(shortUrlCode);
      if(!ObjectUtils.isEmpty(shortenUrlMapping)) {
          return shortenUrlMapping.getOriginalUrl();
      } else {
          throw new UrlNotFoundException("URL not found");
      }
    }

}
