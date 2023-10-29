package com.github.photomanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class PhotoMetadata {
    private final LocalDateTime photoCreationDateTime;
    private final Double longitude;
    private final Double latitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoMetadata that = (PhotoMetadata) o;
        return Objects.equals(photoCreationDateTime, that.photoCreationDateTime) && Objects.equals(longitude, that.longitude) && Objects.equals(latitude, that.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoCreationDateTime, longitude, latitude);
    }

    @Override
    public String toString() {
        return "PhotoMetadata{" +
                "dateTimeOriginal=" + photoCreationDateTime +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
