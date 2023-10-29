package com.github.photomanager.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DateDirectoryData {
    private final String yearDirectory;
    private final String monthDirectory;
}
