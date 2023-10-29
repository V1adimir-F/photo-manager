package com.github.photomanager.metadata_utils;

import com.github.photomanager.model.PhotoMetadata;

import java.nio.file.Path;
import java.util.Optional;

/**
 * The `PhotoMetadataExtractor` interface defines a contract for classes that can extract metadata from a photo file.
 *
 * @author Vladimir F.
 */
public interface PhotoMetadataExtractor {

    Optional<PhotoMetadata> extractMetadata(Path file);
}
