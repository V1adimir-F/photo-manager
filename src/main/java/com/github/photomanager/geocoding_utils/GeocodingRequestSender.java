package com.github.photomanager.geocoding_utils;

import java.io.IOException;
import java.net.http.HttpResponse;

/**
 * The `GeocodingRequestSender` interface defines a contract for classes
 * that will send geocoding requests and receive the response.
 *
 * @author Vladimir F.
 */
public interface GeocodingRequestSender {

    HttpResponse<String> fetchGeocodingData(Double longitude, Double latitude) throws IOException, InterruptedException;
}
