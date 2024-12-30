package com.yonymarian.recipe_api_demo.utils;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    //Default message if none provided
    public ApiException() {
        super("Unspecified error encountered while using the API");
    }

}
