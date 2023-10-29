package com.github.photomanager.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.photomanager.dto.response.AllocatedPhotoResponseDto;
import com.github.photomanager.exception.BadRequestException;
import com.github.photomanager.file_utils.FileSearcher;
import com.github.photomanager.geocoding_utils.GeocodingRequestSender;
import com.github.photomanager.metadata_utils.PhotoMetadataExtractor;
import com.github.photomanager.model.PhotoMetadata;
import com.github.photomanager.model.geocoding_entity.Response;
import com.github.photomanager.model.geocoding_entity.Root;
import com.github.photomanager.service.PhotoManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The PhotoManagerServiceImpl class is an implementation of the PhotoManagerService interface.
 * Distributes photo files into directories.
 *
 * @author Vladimir F.
 * @see PhotoManagerService
 */
@Service
public class PhotoManagerServiceImpl implements PhotoManagerService {

    private final FileSearcher fileSearcher;
    private final PhotoMetadataExtractor photoMetadataExtractor;
    private final GeocodingRequestSender geocodingRequestSender;

    @Autowired
    public PhotoManagerServiceImpl(FileSearcher fileSearcher,
                                   PhotoMetadataExtractor photoMetadataExtractor,
                                   GeocodingRequestSender geocodingRequestSender) {
        this.fileSearcher = fileSearcher;
        this.photoMetadataExtractor = photoMetadataExtractor;
        this.geocodingRequestSender = geocodingRequestSender;
    }

    /**
     * The `allocatePhoto` method takes all photo files from 'sourceDirectory' directory and
     * copies them into 'targetDirectory' directory. At the same time, it creates subfolders
     * according to the date and place of creation of the photo.
     *
     * @param sourceDirectory the path to the directory from which the method takes files
     * @param targetDirectory the path to the directory to which the method copies files
     * @return an instance of the {@code AllocatedPhotoResponseDto} class
     * @see PhotoManagerService
     * @see AllocatedPhotoResponseDto
     */
    @Override
    public AllocatedPhotoResponseDto allocatePhoto(String sourceDirectory, String targetDirectory) {
        final var sourceRootDirectoryPath = getPathFromString(sourceDirectory);
        final var targetRootDirectoryPath = getPathFromString(targetDirectory);

        final List<Path> fileList;
        try {
            fileList = fileSearcher.searchFilesByWalkFileTree(sourceRootDirectoryPath);
        } catch (IOException exception) {
            throw new BadRequestException(exception.getMessage());
        }

        final int allPhotoCount = fileList.size();
        int copiedPhotoCount = 0;

        final HashMap<Path, Optional<PhotoMetadata>> filePhotoMetadataHashMap = fileList.stream()
                .collect(Collectors.toMap(
                        Function.identity(),
                        photoMetadataExtractor::extractMetadata,
                        (e1, e2) -> e1,
                        HashMap::new));

        for (Map.Entry<Path, Optional<PhotoMetadata>> filePhotoMetadataEntry : filePhotoMetadataHashMap.entrySet()) {
            Path targetDirectoryPath = targetRootDirectoryPath;
            final var photoMetadataOptional = filePhotoMetadataEntry.getValue();
            if (photoMetadataOptional.isPresent()) {
                PhotoMetadata photoMetadata = photoMetadataOptional.get();
                List<String> pathNameList = new ArrayList<>();

                Optional<LocalDateTime> photoCreationDate = Optional.ofNullable(photoMetadata.getPhotoCreationDateTime());
                photoCreationDate.ifPresent(dateTime -> this.addDateToPathList(pathNameList, dateTime));

                Optional<Double> longitudeOptional = Optional.ofNullable(photoMetadata.getLongitude());
                Optional<Double> latitudeOptional = Optional.ofNullable(photoMetadata.getLatitude());
                if (longitudeOptional.isPresent() && latitudeOptional.isPresent()) {
                    final var responseOptional = getResponseFromApi(longitudeOptional.get(), latitudeOptional.get());
                    if (responseOptional.isPresent()) {
                        final var photoCreationPlace = getPhotoCreationPlace(responseOptional.get());
                        photoCreationPlace.ifPresent(pathNameList::add);
                    }
                }

                for (String path : pathNameList) {
                    targetDirectoryPath = targetDirectoryPath.resolve(path);
                }
            }

            if (copyToTargetPath(filePhotoMetadataEntry.getKey(), targetDirectoryPath)) {
                copiedPhotoCount++;
            }
        }

        return new AllocatedPhotoResponseDto(allPhotoCount, copiedPhotoCount);
    }

    /**
     * Takes a string representing a directory path and converts it to a Path object. Throws an exception if
     * the directory does not exist or is not a valid directory.
     *
     * @param directoryString a string that represents the path to a directory.
     * @return a Path object.
     * @throws BadRequestException if the directory does not exist or is not a valid directory.
     */
    private Path getPathFromString(String directoryString) {
        Path directoryPath = Paths.get(directoryString);
        if (Files.notExists(directoryPath) || !Files.isDirectory(directoryPath)) {
            throw new BadRequestException("Directory:" + directoryString + " is invalid");
        }
        return directoryPath;
    }

    /**
     * Adds a string representation the year and month of a given LocalDateTime object to a list of strings.
     *
     * @param list a List of Strings where a string representation of the date components will be added.
     * @param dateTime the `dateTime` parameter is of type `LocalDateTime`, which represents a date and time without a time
     * zone. It contains information about the year, month, day, hour, minute, second, and nanosecond.
     */
    private void addDateToPathList(List<String> list, LocalDateTime dateTime) {
        String yearString = String.valueOf(dateTime.getYear());
        list.add(yearString);
        String monthString = dateTime.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        list.add(monthString);
    }

    /**
     * Fetches geocoding data from a Yandex API using longitude and latitude coordinates and returns an optional
     * response.
     *
     * @param longitude The longitude parameter represents the geographic coordinate of a location on the Earth's surface,
     * measured in degrees east or west from the Prime Meridian. It specifies the east-west position of the location.
     * @param latitude The latitude parameter represents the geographic coordinate of a location on the Earth's surface,
     * measured in degrees north or south of the equator. It specifies the north-south position of the location.
     * @return a `Optional<Response>` with geocoding data.
     */
    private Optional<Response> getResponseFromApi(Double longitude, Double latitude) {
        try {
            final HttpResponse<String> httpResponse = geocodingRequestSender.fetchGeocodingData(longitude, latitude);
            ObjectMapper objectMapper = new ObjectMapper();
            final var root = objectMapper.readValue(httpResponse.body(), Root.class);
            return Optional.of(root.getResponse());
        } catch (IOException | InterruptedException exception) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves the photo creation place from a given response object, using the geocoder metadata.
     *
     * @param response is an object of type "Response". It is assumed to contain some data related
     * to a geolocation query.
     * @return an Optional<String> object with data about the place where the photo was created
     * or an empty Optional if the data could not be retrieved.
     */
    private Optional<String> getPhotoCreationPlace(Response response) {
        final var geoObjectCollection = response.getGeoObjectCollection();
        final var featureMember = geoObjectCollection.getFeatureMember();
        if (!featureMember.isEmpty()) {
            final var geoObject = featureMember.get(0).getGeoObject();
            final var metaDataProperty = geoObject.getMetaDataProperty();
            final var geocoderMetaData = metaDataProperty.getGeocoderMetaData();
            return Optional.of(geocoderMetaData.getText());
        }
        return Optional.empty();
    }

    /**
     * Copies a file to a target directory, creating the directory if it doesn't exist.
     *
     * @param filePath is the path to the file to copy to the target directory.
     * @param targetDirectoryPath is the path to the directory where to copy the
     * file to.
     * @return true if the file was successfully copied, otherwise return false.
     */
    private boolean copyToTargetPath(Path filePath, Path targetDirectoryPath) {
        try {
            Files.createDirectories(targetDirectoryPath);
            targetDirectoryPath = targetDirectoryPath.resolve(filePath.getFileName());
            Files.copy(filePath, targetDirectoryPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException exception) {
            return false;
        }
        return true;
    }
}
