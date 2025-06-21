package com.betonamura.recommend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error response model.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private boolean success = false;
    private String message;
    private String details;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }

    public static ErrorResponse of(String message, String details) {
        return new ErrorResponse(false, message, details);
    }
}
