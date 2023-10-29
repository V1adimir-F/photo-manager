package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Locality {
    @JsonProperty("LocalityName")
    public String localityName;

    @JsonProperty("Thoroughfare")
    public Thoroughfare thoroughfare;
}
