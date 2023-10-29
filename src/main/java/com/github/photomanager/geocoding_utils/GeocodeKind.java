package com.github.photomanager.geocoding_utils;

/**
 * Enumeration `GeocodeKind` represents different kinds of parameter 'kind' of Yandex Geocoding API,
 * such as "house", "street", "metro", "district" and "locality".
 *
 * @author Vladimir F.
 * @see <a href="https://yandex.ru/dev/geocode/doc/ru/request#coords">Request format</a>
 */
public enum GeocodeKind {
    HOUSE("house"),
    STREET("street"),
    METRO("metro"),
    DISTRICT("district"),
    LOCALITY("locality");

    private final String kind;

    GeocodeKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }
}
