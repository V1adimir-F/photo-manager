package com.github.photomanager.geocoding_utils;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

/**
 * The YandexApiUriConfigurer class an implementation of the {@code UriConfigurer} interface.
 * Performs configuring URI by 'longitude' and 'latitude' parameters.
 *
 * @author Vladimir F.
 * @see UriConfigurer
 */
@Component
public class YandexApiUriConfigurer implements UriConfigurer {

    @Value("${geocoding.yandex_api_base_url}")
    private String yandexApiBaseUrl;

    @Value("${geocoding.apikey}")
    private String apiKey;

    @Value("${geocoding.results}")
    private String results;

    @Value("${geocoding.format}")
    private String format;

    @Value("${geocoding.kind}")
    private String kind;

    @Value("${geocoding.lang}")
    private String lang;

    private URI uri;

    /**
     * Configures the URI with the parameters specified in the application.yml file.
     */
    @PostConstruct
    protected void init() {
        kind = Arrays.stream(GeocodeKind.values()).anyMatch(geocodeKind -> geocodeKind.getKind().equals(kind)) ? kind : GeocodeKind.STREET.getKind();
        lang = Arrays.stream(GeocodeLang.values()).anyMatch(geocodeLang -> geocodeLang.getLang().equals(lang)) ? lang : GeocodeLang.RU_RU.getLang();

        uri = UriComponentsBuilder.fromHttpUrl(yandexApiBaseUrl)
                .queryParam("apikey", apiKey)
                .queryParam("geocode", "")
                .queryParam("kind", kind)
                .queryParam("lang", lang)
                .queryParam("results", results)
                .queryParam("format", format)
                .build()
                .toUri();
    }

    /**
     * Takes longitude and latitude coordinates as input and configures a URI by replacing the "geocode" query
     * parameter with the formatted coordinates.
     *
     * @param longitude the geographic coordinate of a location's east-west position on
     * the Earth's surface.
     * @param latitude the geographic coordinate of a location on the Earth's surface. It specifies the north-south position of the location.
     * @return a URI object.
     */
    public URI configureByCoordinates(Double longitude, Double latitude) {
        uri = UriComponentsBuilder.fromUri(uri)
                .replaceQueryParam("geocode", String.format("%.14f,%.14f", longitude, latitude))
                .build()
                .toUri();
        return uri;
    }
}
