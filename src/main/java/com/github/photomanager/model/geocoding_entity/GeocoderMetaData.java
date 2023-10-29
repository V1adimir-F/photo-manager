package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GeocoderMetaData {
    public String precision;
    public String text;
    public String kind;

    @JsonProperty("Address")
    public Address address;

    @JsonProperty("AddressDetails")
    public AddressDetails addressDetails;
}
