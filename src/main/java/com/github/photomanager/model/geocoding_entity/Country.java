package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Country {
    @JsonProperty("AddressLine")
    public String addressLine;

    @JsonProperty("CountryNameCode")
    public String countryNameCode;

    @JsonProperty("CountryName")
    public String countryName;

    @JsonProperty("AdministrativeArea")
    public AdministrativeArea administrativeArea;
}
