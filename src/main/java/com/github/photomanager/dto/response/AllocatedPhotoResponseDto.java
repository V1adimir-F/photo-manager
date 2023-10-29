package com.github.photomanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * The AllocatedPhotoResponseDto class is used to represent a response from server
 * with result of allocating photo:
 * 'photoCount' - all photo count given for allocating,
 * 'allocatedPhotoCount' - allocated photo count.
 *
 * @author Vladimir F.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllocatedPhotoResponseDto {
    private int photoCount;
    private int allocatedPhotoCount;
}
