package com.dkb.url.shortening.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RetrieveUrlResponse {

    private String originalUrl;
}
