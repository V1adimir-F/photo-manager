package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GeocoderResponseMetaData {
    @JsonProperty("Point")
    public Point point;
    public BoundedBy boundedBy;
    public String request;
    public String results;
    public String found;
}
