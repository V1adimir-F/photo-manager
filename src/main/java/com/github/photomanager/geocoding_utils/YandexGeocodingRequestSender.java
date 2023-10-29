package com.github.photomanager.geocoding_utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * The YandexGeocodingRequestSender class is an implementation of the {@code GeocodingRequestSender} interface for sending
 * geocoding requests to the Yandex Geocoding API.
 *
 * @author Vladimir F.
 * @see GeocodingRequestSender
 */
@Component
public class YandexGeocodingRequestSender implements GeocodingRequestSender {
    private static final long DURATION = 10L;

    private final UriConfigurer uriConfigurer;

    @Autowired
    public YandexGeocodingRequestSender(UriConfigurer uriConfigurer) {
        this.uriConfigurer = uriConfigurer;
    }

    /**
     * Sends geocoding request to the Yandex Geocoding API and fetching the geocoding data for a given longitude and latitude.
     *
     * @param longitude the geographic coordinate of a location's east-west position on the Earth's surface.
     * @param latitude the geographic coordinate of a location on the Earth's surface. It specifies the north-south position of the location.
     * @return response from Yandex Geocoding API with geocoding data
     * @throws IOException if an I/O error occurs when sending or receiving (when invoking 'httpClient.send' method)
     * @throws InterruptedException if the operation is interrupted (when invoking 'httpClient.send' method)
     */
    @Override
    public HttpResponse<String> fetchGeocodingData(Double longitude, Double latitude) throws IOException, InterruptedException {
        final var apiUri = uriConfigurer.configureByCoordinates(longitude, latitude);
        final var httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(apiUri)
                .timeout(Duration.of(DURATION, ChronoUnit.SECONDS))
                .build();

        final var httpClient = HttpClient.newHttpClient();
        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    }
}
