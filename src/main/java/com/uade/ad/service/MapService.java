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

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @returns Distance in Meters
     */
    public double calculateDistance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = 0;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}
