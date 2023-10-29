package com.github.photomanager.service;

import com.github.photomanager.dto.response.AllocatedPhotoResponseDto;

/**
 * The `PhotoManagerService` interface is defining a contract for a service that manages photos.
 * It declares one method `allocatePhoto` that takes two parameters of type `String` and returns
 * an `AllocatedPhotoResponseDto` object. Classes that implement this interface will need to provide
 * an implementation for the `allocatePhoto` method.
 *
 * @author Vladimir F.
 */
public interface PhotoManagerService {

    AllocatedPhotoResponseDto allocatePhoto(String sourceDirectory, String targetDirectory);
}
