package com.uade.ad.service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
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


    public LatLng getLocationFromAddress(final String address, final String postalCode, final String city,
                                         final String province, final String country) {
        try {
            String addressResult = String.format("%s, %s, %s, %s, %s", address, postalCode, city, province, country);
            GeocodingResult[] results = GeocodingApi.geocode(geoApiContext, addressResult).await();
            return results[0].geometry.location;
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
