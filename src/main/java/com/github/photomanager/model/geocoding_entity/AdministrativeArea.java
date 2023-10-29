package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdministrativeArea {
    @JsonProperty("AdministrativeAreaName")
    public String administrativeAreaName;

    @JsonProperty("SubAdministrativeArea")
    public SubAdministrativeArea subAdministrativeArea;
}
