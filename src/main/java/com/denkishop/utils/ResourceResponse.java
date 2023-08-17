package com.denkishop.utils;

public class ResourceResponse<T> {
    private T resource;
    private String message;

    public ResourceResponse(T resource, String message) {
        this.resource = resource;
        this.message = message;
    }

    public T getResource() {
        return resource;
    }

    public String getMessage() {
        return message;
    }

    public void setResource(T resource) {
        this.resource = resource;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
