package com.example.empiretechtestbackendjava.exception;

import java.io.IOException;

public class ImageSaveException extends RuntimeException {
    public ImageSaveException(String message, IOException exception) {
        super(message, exception);
    }

    public ImageSaveException(String message) {
        super(message);
    }
}
