package com.betonamura.recommend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic success response wrapper.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Success<T> {
    private boolean success;
    private T data;
    private String message;

    public static <T> Success<T> of(T data) {
        return new Success<>(true, data, "Success");
    }

    public static <T> Success<T> of(T data, String message) {
        return new Success<>(true, data, message);
    }
}
