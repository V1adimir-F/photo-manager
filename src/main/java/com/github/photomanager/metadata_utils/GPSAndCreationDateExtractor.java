package com.github.photomanager.metadata_utils;

import com.github.photomanager.model.PhotoMetadata;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * The GPSAndCreationDateExtractor class is an implementation of the {@code PhotoMetadataExtractor} interface.
 * Performs extracting GPS coordinates and creation dates from photo file.
 *
 * @author Vladimir F.
 * @see PhotoMetadataExtractor
 */
@Component
public class GPSAndCreationDateExtractor implements PhotoMetadataExtractor {

    private static final String DATE_TIME_PATTERN = "''yyyy:MM:dd HH:mm:ss''";

    enum GpsTag {
        LONGITUDE,
        LATITUDE
    }

    /**
     * Takes a `Path` object representing a file as a parameter and
     * returns an `Optional` object containing `PhotoMetadata`
     *
     * @param file from which metadata is extracted
     * @return Optional<PhotoMetadata> `Optional` object containing `PhotoMetadata`
     */
    @Override
    public Optional<PhotoMetadata> extractMetadata(Path file) {
        Optional<PhotoMetadata> result = Optional.empty();
        if (!Files.isRegularFile(file)) {
            return result;
        }

        try {
            final ImageMetadata metadata = Imaging.getMetadata(file.toFile());
            if (metadata instanceof final JpegImageMetadata jpegMetadata) {
                LocalDateTime photoCreationDateTime = getPhotoCreationDateTime(jpegMetadata);
                Double longitude = getGPSData(jpegMetadata, GpsTag.LONGITUDE);
                Double latitude = getGPSData(jpegMetadata, GpsTag.LATITUDE);
                result = Optional.of(new PhotoMetadata(photoCreationDateTime, longitude, latitude));
            }
            return result;
        } catch (ImageReadException | IOException e) {
            return result;
        }
    }

    /**
     * Retrieves the creation date and time of a photo from its EXIF metadata.
     *
     * @param jpegMetadata the parameter "jpegMetadata" is of type JpegImageMetadata. It is used to retrieve the EXIF
     * metadata from a JPEG image.
     * @return LocalDateTime object, which represents the date and time of the photo's creation.
     * If the creation date and time cannot be parsed from the EXIF metadata, the method returns null.
     */
    private LocalDateTime getPhotoCreationDateTime(JpegImageMetadata jpegMetadata) {
        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        try {
            String creationDateTimeAsString = field.getValueDescription();
            return LocalDateTime.parse(creationDateTimeAsString, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Retrieves GPS data from a JPEG image's EXIF metadata based on the specified GPS tag.
     *
     * @param jpegMetadata an object of type `JpegImageMetadata` which represents the
     * metadata of a JPEG image. It contains various information about the image, including the Exif metadata.
     * @param gpsTag an enum that specifies the longitude or latitude GPS data. It can have two possible values: `LONGITUDE` or `LATITUDE`.
     * @return a Double value, that represents longitude or latitude value.
     */
    private Double getGPSData(JpegImageMetadata jpegMetadata, GpsTag gpsTag) {
        final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
        if (null != exifMetadata) {
            try {
                final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                if (null != gpsInfo) {
                    return switch (gpsTag) {
                        case LONGITUDE -> gpsInfo.getLongitudeAsDegreesEast();
                        case LATITUDE -> gpsInfo.getLatitudeAsDegreesNorth();
                    };
                }
            } catch (ImageReadException e) {
                return null;
            }
        }
        return null;
    }
}
