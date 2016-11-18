package com.greilly.note.exception;

/**
 * Created by Greg on 11/18/2016.
 */
public class ResponseError {
    private String message;

    public ResponseError(Exception e) {
        this.message = e.getMessage();
    }

    public String getMessage() {
        return this.message;
    }
}
