package com.github.photomanager.geocoding_utils;

import java.net.URI;

/**
 * The `UriConfigurer` interface is defining a contract for classes that will
 * provide a method to configure a URI based on coordinates (longitude and latitude).
 *
 * @author Vladimir F.
 */
public interface UriConfigurer {

    URI configureByCoordinates(Double longitude, Double latitude);
}
