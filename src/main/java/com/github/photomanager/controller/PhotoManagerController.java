package com.github.photomanager.controller;

import com.github.photomanager.dto.request.DirectoryPathRequestDto;
import com.github.photomanager.service.PhotoManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/photo/manager")
public class PhotoManagerController {

    private final PhotoManagerService photoManagerService;

    @Autowired
    public PhotoManagerController(PhotoManagerService photoManagerService) {
        this.photoManagerService = photoManagerService;
    }

    @PostMapping
    public ResponseEntity<?> allocatePhoto(@RequestBody DirectoryPathRequestDto directoryPathRequestDto) {
        final var allocatedPhotoResponseDto = photoManagerService.allocatePhoto(
                directoryPathRequestDto.getSourceDirectory(),
                directoryPathRequestDto.getTargetDirectory());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allocatedPhotoResponseDto);
    }
}
