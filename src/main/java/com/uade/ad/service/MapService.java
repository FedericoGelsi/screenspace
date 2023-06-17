package com.uade.ad.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.uade.ad.controller.dto.CinemaCreateDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    @Value("${google.map.api.key}")
    private String apiKey;

    private final GeoApiContext geoApiContext;

    public MapService(final GeoApiContext geoApiContext) {
        this.geoApiContext = geoApiContext;
    }


    public LatLng getLocationFromAddress(final String calle, final String numero, final String localidad,
                                         final String provincia, final String pais) {
        try {
            String address = String.format("%s %s, %s, %s, %s", calle, numero, localidad, provincia, pais);
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, address).await();
            return results[0].geometry.location;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
