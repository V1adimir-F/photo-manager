package com.github.photomanager.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The ErrorDetailsResponse class is used to represent detailed information about an error.
 *
 * @author Vladimir F.
 */
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetailsResponse {
    private String errorMessage;
}
