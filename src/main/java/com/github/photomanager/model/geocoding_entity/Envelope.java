package com.github.photomanager.model.geocoding_entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Envelope {
    public String lowerCorner;
    public String upperCorner;
}
