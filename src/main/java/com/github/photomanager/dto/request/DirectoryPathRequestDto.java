package com.github.photomanager.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The DirectoryPathRequestDto class is used to represent a request with
 * source directory path and target directory path.
 *
 * @author Vladimir F.
 */
@Getter
@Setter
@NoArgsConstructor
public class DirectoryPathRequestDto {
    String sourceDirectory;
    String targetDirectory;
}
