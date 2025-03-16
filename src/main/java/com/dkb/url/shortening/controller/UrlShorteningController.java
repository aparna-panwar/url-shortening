package com.dkb.url.shortening.controller;

import com.dkb.url.shortening.model.dto.RetrieveUrlResponse;
import com.dkb.url.shortening.model.dto.ShortenUrlResponse;
import com.dkb.url.shortening.model.dto.ShorteningUrlRequest;
import com.dkb.url.shortening.service.UrlShorteningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UrlShorteningController{

    private final UrlShorteningService urlShorteningService;

    @PostMapping("/shorten")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShorteningUrlRequest request) {
        return ResponseEntity.ok(new ShortenUrlResponse(urlShorteningService.shortenUrl(request.getOriginalUrl())));
    }

    @GetMapping("/retrieve/{shortUrl}")
    public ResponseEntity<RetrieveUrlResponse> retrieveUrl(@PathVariable String shortUrl) {
        return ResponseEntity.ok(new RetrieveUrlResponse(urlShorteningService.getRetrieveUrl(shortUrl)));
    }
}
