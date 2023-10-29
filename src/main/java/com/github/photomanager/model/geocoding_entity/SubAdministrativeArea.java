package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubAdministrativeArea {
    @JsonProperty("SubAdministrativeAreaName")
    public String subAdministrativeAreaName;

    @JsonProperty("Locality")
    public Locality locality;
}
