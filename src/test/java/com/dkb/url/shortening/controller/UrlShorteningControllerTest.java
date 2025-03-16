package com.dkb.url.shortening.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.dkb.url.shortening.UrlShorteningApplication;
import com.dkb.url.shortening.exception.UrlNotFoundException;
import com.dkb.url.shortening.model.dto.ShorteningUrlRequest;
import com.dkb.url.shortening.model.entity.UrlShortenMapping;
import com.dkb.url.shortening.repository.UrlShorteningRepository;
import com.dkb.url.shortening.service.UrlShorteningService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UrlShorteningApplication.class, properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
@Transactional
public class UrlShorteningControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlShorteningService urlShorteningService;

    @Autowired
    private UrlShorteningRepository urlShorteningRepository;

    private static final String BASE_URL = "/api";

    @Test
    public void testShortenUrl() throws Exception {
        String originalUrl = "https://mylongurl.com";
        ShorteningUrlRequest request = new ShorteningUrlRequest(originalUrl);

        mockMvc.perform(post(BASE_URL+"/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortUrl").exists());
    }

    @Test
    public void testRedirectionUrlValidShortUrl() throws Exception {
        String shortUrlCode = "QWY67Ht";
        String actualUrl = "https://mylongurl.com";

        urlShorteningRepository.save(new UrlShortenMapping(actualUrl, shortUrlCode));
        mockMvc.perform(get(BASE_URL + "/retrieve/{shortUrl}", shortUrlCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value(actualUrl));
    }

    @Test
    public void testRedirectionUrlInvalidShortUrl() throws Exception {
        mockMvc.perform(get(BASE_URL + "/retrieve/{shortUrl}", "nonavailableurl"))
                .andExpect(status().isNotFound())
                .andExpect(result -> {
                    assertTrue(result.getResolvedException() instanceof UrlNotFoundException);
                    assertEquals("URL not found", result.getResolvedException().getMessage());
                });
    }

}