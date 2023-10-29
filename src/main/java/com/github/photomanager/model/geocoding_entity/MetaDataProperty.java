package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetaDataProperty {
    @JsonProperty("GeocoderResponseMetaData")
    public GeocoderResponseMetaData geocoderResponseMetaData;

    @JsonProperty("GeocoderMetaData")
    public GeocoderMetaData geocoderMetaData;
}
