package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class Address {
    @JsonProperty("country_code")
    public String countryCode;

    public String formatted;

    @JsonProperty("Components")
    public ArrayList<Component> components;
}
