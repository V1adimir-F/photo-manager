package com.github.photomanager.model.geocoding_entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GeoObject {
    public MetaDataProperty metaDataProperty;
    public String name;
    public String description;
    public BoundedBy boundedBy;
    public String uri;

    @JsonProperty("Point")
    public Point point;
}
