package com.example.demo.models;

public class ImagePath {
    private String description;
    private String path;

    public ImagePath(String description, String path) {
        this.description = description;
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
