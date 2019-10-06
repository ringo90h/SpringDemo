package com.example.eatgo.domain;

public class RestourantNotFoundException extends RuntimeException {
    public RestourantNotFoundException(Long id) {
        super("Could not find restaurant "+id);
    }
}
