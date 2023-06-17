package com.uade.ad.config;

import com.google.maps.GeoApiContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapConfig {

    @Value("${google.map.api.key}")
    private String apiKey;

    @Bean
    public GeoApiContext getGeoApiContext() {
        return new GeoApiContext.Builder().apiKey(apiKey).build();
    }
}
