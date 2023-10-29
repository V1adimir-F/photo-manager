package com.github.photomanager.geocoding_utils;

/**
 * Enumeration `GeocodeLang` is an enumeration that represents
 * different kinds of parameter 'lang' of Yandex Geocoding API (languages for geocoding purposes).
 *
 * @author Vladimir F.
 * @see <a href="https://yandex.ru/dev/geocode/doc/ru/request#coords">Request format</a>
 */
public enum GeocodeLang {
    RU_RU("ru_RU"),
    UK_UA("uk_UA"),
    BE_BY("be_BY"),
    EN_RU("en_RU"),
    EN_US("en_US"),
    TR_TR("tr_TR");

    private final String lang;

    GeocodeLang(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }
}
